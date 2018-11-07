package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Locatie(
    val locatieId: Int,
    val straat: String,
    val huisnummer: String,
    val postcode: String,
    val gemeente: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable