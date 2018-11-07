package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    val tagId: Int,
    val naam: String,
    val kleur: String
) : Parcelable