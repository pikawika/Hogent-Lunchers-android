package hogent.be.lunchers.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * De @Entity annotatie wordt gebruikt om aan te tonen dat dit domein object can gebruikt worden door Room.
 * De tabelName parameter duidt de naam van de tabel aan waarvan objecten van dit type opgeslagen worden.
 */
@Entity(tableName = "order_table")
data class Reservatie(
    // Elke entiteit heeft behoefte aan een primaire sleutel
    @PrimaryKey val reservatieId: Int,
    val aantal: Int,
    val lunch: Lunch,
    val datum: Date,
    val status: Int)