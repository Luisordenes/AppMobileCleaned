package roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import roomDatabase.dao.DaoUsuario
import roomDatabase.entity.Usuario
import roomDatabase.dao.DaoCita
import roomDatabase.entity.Cita

@Database(
    entities = [Usuario::class, Cita::class],
    version = 1
)
abstract class Db: RoomDatabase(){
    abstract fun daoUsuario(): DaoUsuario
    abstract fun daoCita(): DaoCita
}