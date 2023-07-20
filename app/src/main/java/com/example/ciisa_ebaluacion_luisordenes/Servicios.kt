package com.example.ciisa_ebaluacion_luisordenes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ciisa_ebaluacion_luisordenes.MenuBotones
import com.example.ciisa_ebaluacion_luisordenes.R

class Servicios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        val btn_back = findViewById<Button>(R.id.btn_back)

        val user:String = intent.getStringExtra("user").toString()

        btn_back.setOnClickListener {
            val intent = Intent(this@Servicios, MenuBotones::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }
    }
}