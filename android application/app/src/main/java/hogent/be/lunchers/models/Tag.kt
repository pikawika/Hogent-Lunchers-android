package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Een [Tag] van een [Lunch] verbonden via [LunchTag].
 *
 * Deze kunnen weggefilterd worden door een bijhorend [BlacklistedItem]
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
@Parcelize
data class Tag(
    val tagId: Int,
    @field:Json(name = "naam") val name: String,
    @field:Json(name = "kleur") val color: String
) : Parcelable