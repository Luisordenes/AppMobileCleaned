package roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import roomDatabase.entity.Usuario

@Dao
interface DaoUsuario {
    @Query("SELECT * FROM Usuario")
    suspend fun obtenerUsuarios():List<Usuario>

    @Query("SELECT * FROM Usuario WHERE usuario=:usuario")
    suspend fun obtenerUsuario(usuario: String):List<Usuario>

    @Query("SELECT * FROM Usuario WHERE usuario=:usuario AND contrasena=:contrasena")
    suspend fun login(usuario: String, contrasena: String): List<Usuario>

    @Insert
    suspend fun agregarUsuario(usuario: Usuario):Long

    @Query("UPDATE  Usuario SET contrasena=:contrasena WHERE usuario=:usuario")
    suspend fun actualizarUsuario(usuario: String,contrasena:String): Int

    @Query("DELETE FROM Usuario WHERE usuario=:usuario")
    suspend fun borrarUsuario(usuario: String)
}