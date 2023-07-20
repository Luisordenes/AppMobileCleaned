package roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cita {
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
    var dir:  String? = null
    var date: String? = null
    var time: String? = null
    var servicio: String? = null
    var user: String? = null

    constructor(
        dir: String?,
        date: String?,
        time: String?,
        servicio: String?,
        user: String?
    ) {
        this.dir = dir
        this.date = date
        this.time = time
        this.servicio = servicio
        this.user = user
    }



}