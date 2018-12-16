package hogent.be.lunchers.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

/**
 * De @Entity annotatie wordt gebruikt om aan te tonen dat dit domein object can gebruikt worden door Room.
 * De tabelName parameter duidt de name van de tabel aan waarvan objecten van dit type opgeslagen worden.
 */
@Entity(tableName = "order_table")
data class Order(
    // Elke entiteit heeft behoefte aan een primaire sleutel
    @field:Json(name = "reservatieId") @PrimaryKey val reservationId: Int,
    @field:Json(name = "aantal") val amount: Int,
    val lunch: Lunch,
    @field:Json(name = "datum") val date: Date,
    val status: Int,
    @field:Json(name = "opmerking") val message: String
)