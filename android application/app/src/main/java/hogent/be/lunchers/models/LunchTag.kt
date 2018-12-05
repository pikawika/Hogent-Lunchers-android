package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LunchTag(
    val lunchId: Int,
    val tagId: Int,
    val tag: Tag
) : Parcelable