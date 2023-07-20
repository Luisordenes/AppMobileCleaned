package com.example.ciisa_ebaluacion_luisordenes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDatabase.Db

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()

        val til_user = findViewById<TextInputLayout>(R.id.til_user)
        val til_pass = findViewById<TextInputLayout>(R.id.til_pass)
        val cb_recorder = findViewById<CheckBox>(R.id.cb_recorder)
        val btn_init = findViewById<Button>(R.id.btn_init)
        val btn_register = findViewById<Button>(R.id.btn_register)

        val preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)

        til_user.editText?.setText(preferencias.getString("user",""))

        btn_init.setOnClickListener {
            var user = til_user.editText?.text.toString()
            var pass = til_pass.editText?.text.toString()
            var isRemember = cb_recorder.isChecked

            if (validarCampos()==0){
                val editor = preferencias.edit()
                if(isRemember){
                    editor.putString("user",user)
                    editor.commit()
                }else{
                    editor.putString("user","")
                    editor.commit()
                }
                lifecycleScope.launch{
                    var response = room.daoUsuario().login(user,pass)
                    if (response.size == 1) {
                        Toast.makeText(this@MainActivity,"Login exitoso", Toast.LENGTH_SHORT).show()
                        til_user.error = ""
                        til_pass.error = ""
                        val intent = Intent(this@MainActivity, MenuBotones::class.java)
                        intent.putExtra("user",user)
                        startActivity(intent)
                    }
                    else {
                        til_user.error = getString(R.string.error_invalidate_field)
                        til_pass.error = getString(R.string.error_invalidate_field)
                        Toast.makeText(this@MainActivity,"Login fallido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btn_register.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistroUsuario::class.java)
            startActivity(intent)
        }
    }

    fun validarCampos():Int{
        var contador:Int = 0
        val til_user = findViewById<TextInputLayout>(R.id.til_user)
        val til_pass = findViewById<TextInputLayout>(R.id.til_pass)
        var user = til_user.editText?.text.toString()
        var pass = til_pass.editText?.text.toString()
        val validate = Validate()
        if(validate.validarCampoNulo(user)){
            til_user.error = getString(R.string.error_null_field)
            contador++
        }else{
            if(validate.validarMinCaracter(user)){
                til_user.error = getString(R.string.error_min_field)
                contador++
            }else{
                til_user.error = ""
            }
        }
        if(validate.validarCampoNulo(pass)){
            til_pass.error = getString(R.string.error_null_field)
            contador++
        }else{
            if(validate.validarMinCaracter(pass)){
                til_pass.error = getString(R.string.error_min_field)
                contador++
            }else{
                til_pass.error = ""
            }
        }

        return contador
    }
}