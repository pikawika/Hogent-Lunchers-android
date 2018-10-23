package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Lunch(val lunchId: Int, val naam: String, val prijs: Double, val beschrijving: String, val afbeeldingen: List<Int>, val ingredienten: List<String>, val beginDatum: Date, val eindDatum: Date, val tags: List<Tag>) : Parcelable