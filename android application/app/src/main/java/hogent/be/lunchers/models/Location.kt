package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * De [Location] geeft alle informatie van een locatie, inclusief [longitude] en [latitude].
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
@Parcelize
data class Location(
    @field:Json(name = "locatieId") val locationId: Int,
    @field:Json(name = "straat") val street: String,
    @field:Json(name = "huisnummer") val houseNumber: String,
    @field:Json(name = "postcode") val postalCode: String,
    @field:Json(name = "gemeente") val city: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable