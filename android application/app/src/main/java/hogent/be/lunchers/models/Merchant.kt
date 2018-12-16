package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Een [Merchant] heeft een lijst van [Lunch]es en info van zijn handelszaak
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
@Parcelize
data class Merchant(
    @field:Json(name = "handelsNaam") val companyName: String,
    @field:Json(name = "locatie") val location: Location,
    val website: String,
    val lunches: List<Lunch>,
    @field:Json(name = "promotieRange") val promotionRange: Int,
    @field:Json(name = "gebruikerId") val userId: Int,
    @field:Json(name = "telefoonnummer") val phoneNumber: String,
    val email: String,
    @field:Json(name = "voornaam") val firstName: String,
    @field:Json(name = "achternaam") val lastName: String
) : Parcelable