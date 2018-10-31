package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Handelaar(
    val naam: String,
    val locatie: Locatie,
    val website: String,
    val lunches: List<Lunch>,
    val promotieRange: Int,
    val gebruikerId: Int,
    val telefoonnummer: String,
    val email: String,
    val voornaam: String,
    val achternaam: String
) : Parcelable