package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Afbeelding(
    val afbeeldingId: Int,
    val pad: String
) : Parcelable