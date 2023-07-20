package com.example.ciisa_ebaluacion_luisordenes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDatabase.Db
import roomDatabase.entity.Cita
import java.util.*

class Agendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar)

        val btn_back = findViewById<Button>(R.id.btn_back)
        val btn_agendar = findViewById<Button>(R.id.btn_agendar)
        val til_dir = findViewById<TextInputLayout>(R.id.til_direccion)
        val til_date = findViewById<TextInputLayout>(R.id.til_date)
        val til_time = findViewById<TextInputLayout>(R.id.til_time)
        val spn_servicios = findViewById<Spinner>(R.id.spn_servicios)

        val cal = Calendar.getInstance()

        val user:String = intent.getStringExtra("user").toString()

        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()

        val listenerFecha = DatePickerDialog.OnDateSetListener { datePicker, anyo, mes, dia ->
            var auxDay = ""
            var auxMonth = ""
            if (dia<10) auxDay = "0$dia" else  auxDay = "$dia"
            if (mes<10) auxMonth = "0$mes" else  auxMonth = "$mes"
            til_date.editText?.setText("$auxDay/$auxMonth/$anyo")
        }

        val listenerTime = TimePickerDialog.OnTimeSetListener { timePicker, hour, minutos ->
            var auxHour = ""
            var auxMinutes = ""
            if (hour<10) auxHour = "0$hour" else  auxHour = "$hour"
            if (minutos<10) auxMinutes = "0$minutos" else  auxMinutes = "$minutos"
            til_time.editText?.setText("$auxHour:$auxMinutes")
        }

        til_date.editText?.setOnClickListener {
            DatePickerDialog(this,listenerFecha,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        til_time.editText?.setOnClickListener {
            TimePickerDialog(this,listenerTime,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }

        val arrayAdapterSpinner: ArrayAdapter<*>
        val alcohol = arrayOf("Hogar","Terraza","Vehiculo")
        arrayAdapterSpinner = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,alcohol)
        spn_servicios.adapter = arrayAdapterSpinner

        btn_agendar.setOnClickListener {
            if(validarCampos()==0){
                var dir = til_dir.editText?.text.toString()
                var date = til_date.editText?.text.toString()
                var time = til_time.editText?.text.toString()
                var servicio = spn_servicios.selectedItem.toString()
                var id:Long = 0
                val cita = Cita(dir,date,time,servicio,user)

                lifecycleScope.launch{
                    id = room.daoCita().agregarCita(cita)
                    if(id>0) {
                        Toast.makeText(this@Agendar, "Hora agendada", Toast.LENGTH_SHORT).show()
                        til_dir.error = ""
                        til_date.error = ""
                        til_time.error = ""
                        val intent = Intent(this@Agendar, MiAgenda::class.java)
                        intent.putExtra("user", user)
                        startActivity(intent)
                    }
                }

            }
        }

        btn_back.setOnClickListener {
            val intent = Intent(this@Agendar, MenuBotones::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    fun validarCampos():Int{
        var contador:Int = 0
        val til_dir = findViewById<TextInputLayout>(R.id.til_direccion)
        val til_date = findViewById<TextInputLayout>(R.id.til_date)
        val til_time = findViewById<TextInputLayout>(R.id.til_time)
        var dir = til_dir.editText?.text.toString()
        var date = til_date.editText?.text.toString()
        var time = til_time.editText?.text.toString()
        val validate = Validate()
        if(validate.validarCampoNulo(dir)){
            til_dir.error = getString(R.string.error_null_field)
            contador++
        }else{
            til_dir.error = ""
        }
        if(validate.validarCampoNulo(date)){
            til_date.error = getString(R.string.error_null_field)
            contador++
        }else{
            til_date.error = ""
        }
        if(validate.validarCampoNulo(time)){
            til_time.error = getString(R.string.error_null_field)
            contador++
        }else{
            til_time.error = ""
        }

        return contador
    }
}