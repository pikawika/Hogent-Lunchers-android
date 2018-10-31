package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    @Json(name = "Naam") val naam: String,
    @Json(name = "Kleur")val kleur: String
) : Parcelable