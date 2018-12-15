package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val ingredientId: Int,
    @field:Json(name = "naam") val name: String
) : Parcelable