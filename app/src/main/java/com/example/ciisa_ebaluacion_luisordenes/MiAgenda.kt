package com.example.ciisa_ebaluacion_luisordenes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import roomDatabase.Db
import roomDatabase.entity.Cita

class MiAgenda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_agenda)

        val btn_back = findViewById<Button>(R.id.btn_back)
        val btn_editar = findViewById<Button>(R.id.btn_editar)
        val btn_anular = findViewById<Button>(R.id.btn_anular)
        var lv_datos = findViewById<ListView>(R.id.lv_datos)

        val user:String = intent.getStringExtra("user").toString()

        //Toast.makeText(this@MiAgenda,"Bienvenido a tu agenda ${user}", Toast.LENGTH_SHORT).show()

        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()

        var arrayAdapter:ArrayAdapter<*>

        var citas = ArrayList<String>()

        var response:List<Cita>
        lifecycleScope.launch{
            response = room.daoCita().obtenerCitasUsuario(user)
            for(index in response.indices){
                println(response[index].servicio.toString())
                citas.add(response[index].servicio.toString())
            }

            val arrayAdapter:ArrayAdapter<*> = object : ArrayAdapter<Any?>(
                this@MiAgenda,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                response
            ){
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
                    val view = super.getView(position, convertView, parent)
                    val text1 = view.findViewById<View>(android.R.id.text1) as TextView
                    val text2 = view.findViewById<View>(android.R.id.text2) as TextView
                    text1.setText(response.get(position).servicio)
                    text2.setText(response.get(position).date)
                    return view
                }
            }
            lv_datos.adapter = arrayAdapter
        }



        /*lv_datos.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MiAgenda,"${citas[position]}",Toast.LENGTH_LONG).show()
            }
        }*/

        btn_anular.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¡Alerta!")
            builder.setMessage("Estas seguro de anular esta cita?")
            builder.setPositiveButton("Aceptar"){
                dialog,wich ->
                Toast.makeText(this, "Cita Anulada",Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

        btn_back.setOnClickListener {
            val intent = Intent(this@MiAgenda, MenuBotones::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }
}