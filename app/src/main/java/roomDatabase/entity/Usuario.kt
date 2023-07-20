package roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Usuario {
    @PrimaryKey
    var usuario:String
    var contrasena:String?=null
    var correo:String?=null

    constructor(usuario: String, contrasena:String, correo:String){
        this.usuario = usuario
        this.contrasena = contrasena
        this.correo = correo
    }
}