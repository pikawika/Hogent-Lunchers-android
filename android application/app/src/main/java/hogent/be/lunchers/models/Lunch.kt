package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Lunch(
    val lunchId: Int,
    val naam: String,
    val prijs: Double,
    val ingredienten: List<Ingredient>,
    val beschrijving: String,
    val afbeeldingen: List<Afbeelding>,
    val beginDatum: Date,
    val eindDatum: Date,
    val tags: List<Tag>,
    val handelaar: Handelaar
) : Parcelable