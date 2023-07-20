package com.example.ciisa_ebaluacion_luisordenes

import android.util.Patterns
import java.util.regex.Pattern

class Validate {

    fun validarCampoNulo(texto:String):Boolean{
        return texto.trim().equals("") || texto.trim().length==0
    }

    fun validarMinCaracter(texto:String):Boolean{
        return texto.trim().length<=5
    }

    fun validarCamposIguales(texto:String,texto2: String):Boolean{
        return !texto.trim().equals(texto2.trim())
    }

    fun validarNombre(nombre:String):Boolean{
        val pattern = Pattern.compile("^[a-zA-Z ]+\$")
        return !pattern.matcher(nombre).matches()
    }

    fun validarFormatoCorreo(correo:String):Boolean{
        return !Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

}