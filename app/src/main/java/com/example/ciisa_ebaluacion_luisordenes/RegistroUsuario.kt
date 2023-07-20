package com.example.ciisa_ebaluacion_luisordenes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDatabase.Db
import roomDatabase.entity.Usuario

class RegistroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()

        val til_user = findViewById<TextInputLayout>(R.id.til_user)
        val til_pass = findViewById<TextInputLayout>(R.id.til_pass)
        val til_pass_repeat = findViewById<TextInputLayout>(R.id.til_pass_repeat)
        val til_correo = findViewById<TextInputLayout>(R.id.til_correo)
        val btn_logue = findViewById<Button>(R.id.btn_logue)
        val btn_back = findViewById<Button>(R.id.btn_back)

        btn_logue.setOnClickListener {
            if (validarCampos()==0){
                var user = til_user.editText?.text.toString()
                var pass = til_pass.editText?.text.toString()
                var passRepeat = til_pass_repeat.editText?.text.toString()
                var email = til_correo.editText?.text.toString()
                val usuario = Usuario(user,pass,email)
                lifecycleScope.launch{
                    val id = room.daoUsuario().agregarUsuario(usuario)
                    if(id>0){
                        Log.d("IDuser",id.toString())
                        Toast.makeText(this@RegistroUsuario,"Usuario registrado exitosamente.", Toast.LENGTH_LONG).show()
                        til_user.error = ""
                        til_pass.error = ""
                        til_pass_repeat.error = ""
                        til_correo.error = ""
                    }
                }
                val intent = Intent(this@RegistroUsuario, MainActivity::class.java)
                startActivity(intent)
            }
        }

        btn_back.setOnClickListener {
            val intent = Intent(this@RegistroUsuario, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun validarCampos():Int{
        var contador:Int = 0
        val til_user = findViewById<TextInputLayout>(R.id.til_user)
        val til_pass = findViewById<TextInputLayout>(R.id.til_pass)
        val til_pass_repeat = findViewById<TextInputLayout>(R.id.til_pass_repeat)
        val til_correo = findViewById<TextInputLayout>(R.id.til_correo)
        var user = til_user.editText?.text.toString()
        var pass = til_pass.editText?.text.toString()
        var passRepeat = til_pass_repeat.editText?.text.toString()
        var email = til_correo.editText?.text.toString()
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

        if (validate.validarCampoNulo(pass)){
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

        if (validate.validarCampoNulo(passRepeat)){
            til_pass_repeat.error = getString(R.string.error_null_field)
            contador++
        }else{
            if(validate.validarMinCaracter(passRepeat)){
                til_pass_repeat.error = getString(R.string.error_min_field)
                contador++
            }else{
                til_pass_repeat.error = ""
            }
        }

        if (validate.validarCamposIguales(pass, passRepeat)){
            til_pass.error = getString(R.string.error_equals_field)
            til_pass_repeat.error = getString(R.string.error_equals_field)
            contador++
        }else{
            if(!validate.validarMinCaracter(pass)){
                til_pass.error = ""
            }
            if(!validate.validarMinCaracter(passRepeat)){
                til_pass_repeat.error = ""
            }
        }

        if(validate.validarCampoNulo(email)){
            til_correo.error = getString(R.string.error_null_field)
            contador++
        }
        else{
            if(validate.validarFormatoCorreo(email)){
                til_correo.error = "El correo no tiene el formato correcto"
                contador++
            }
            else{
                til_correo.error = ""
            }
        }

        return contador
    }
}