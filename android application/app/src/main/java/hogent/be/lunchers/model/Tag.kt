package hogent.be.lunchers.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(val naam: String, val kleur: String) : Parcelable