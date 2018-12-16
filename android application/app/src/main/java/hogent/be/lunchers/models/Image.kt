package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Een [Image] van een [Lunch].
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
@Parcelize
data class Image(
    @field:Json(name = "afbeeldingId") val imageId: Int,
    @field:Json(name = "pad") val path: String
) : Parcelable