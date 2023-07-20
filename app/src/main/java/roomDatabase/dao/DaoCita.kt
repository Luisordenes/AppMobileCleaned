package roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import roomDatabase.entity.Cita

@Dao
interface DaoCita {
    @Query("SELECT * FROM Cita")

    suspend fun obtenerCita(): List<Cita>

    @Query("SELECT * FROM Cita WHERE user=:user")
    suspend fun obtenerCitasUsuario(user: String): List<Cita>

    @Insert
    suspend fun agregarCita(cocktail: Cita):Long

    @Query("UPDATE  Cita SET dir=:dir,date=:date,time=:time,servicio=:servicio WHERE id=:id")
    suspend fun actualizarCita(dir:String,date: String,time:String,servicio:String,id:Long): Int

    @Query("DELETE FROM Cita WHERE id=:id")
    suspend fun borrarCita(id: Long)
}