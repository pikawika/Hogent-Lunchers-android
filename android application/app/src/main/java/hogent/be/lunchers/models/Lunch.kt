package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Lunch(
    @Json(name = "LunchId") val lunchId: Int,
    @Json(name = "Naam") val naam: String,
    @Json(name = "Prijs") val prijs: Double,
    @Json(name = "Beschrijving") val beschrijving: String,
    @Json(name = "Afbeeldingen") val afbeeldingen: List<Int>,
    @Json(name = "Ingredienten") val ingredienten: List<String>,
    @Json(name = "BeginDatum") val beginDatum: Date,
    @Json(name = "EindDatum") val eindDatum: Date,
    @Json(name = "Tags") val tags: List<Tag>
) : Parcelable