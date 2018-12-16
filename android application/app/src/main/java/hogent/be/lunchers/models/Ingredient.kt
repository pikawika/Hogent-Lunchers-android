package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Een [Ingredient] van een [Lunch] verbonden via [LunchIngredient].
 *
 * Deze kunnen weggefilterd worden door een bijhorend [BlacklistedItem]
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
@Parcelize
data class Ingredient(
    val ingredientId: Int,
    @field:Json(name = "naam") val name: String
) : Parcelable