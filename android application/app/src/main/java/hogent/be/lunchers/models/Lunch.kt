package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Lunch(
    val lunchId: Int,
    val naam: String,
    val prijs: Double,
    val lunchIngredienten: List<LunchIngredient>,
    val beschrijving: String,
    val afbeeldingen: List<Afbeelding>,
    val beginDatum: Date,
    val eindDatum: Date,
    val lunchTags: List<LunchTag>,
    val handelaar: Handelaar
) : Parcelable