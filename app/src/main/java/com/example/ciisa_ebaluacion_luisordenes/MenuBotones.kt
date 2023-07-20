package com.example.ciisa_ebaluacion_luisordenes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.ciisa_ebaluacion_luisordenes.Servicios

class MenuBotones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_botones)

        val btn_servicios = findViewById<Button>(R.id.btn_servicios)
        val btn_agendarServicio = findViewById<Button>(R.id.btn_agendarServicio)
        val btn_miAgenda = findViewById<Button>(R.id.btn_miAgenda)
        val btn_back = findViewById<Button>(R.id.btn_back)

        val user:String = intent.getStringExtra("user").toString()

        Toast.makeText(this@MenuBotones,"Bienvenido al menu ${user}", Toast.LENGTH_SHORT).show()

        btn_servicios.setOnClickListener {
            val intent = Intent(this@MenuBotones, Servicios::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        btn_agendarServicio.setOnClickListener {
            val intent = Intent(this@MenuBotones, Agendar::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }

        btn_miAgenda.setOnClickListener {
            val intent = Intent(this@MenuBotones, MiAgenda::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            val intent = Intent(this@MenuBotones, MainActivity::class.java)
            startActivity(intent)
        }
    }
}