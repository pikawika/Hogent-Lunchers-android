package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LunchIngredient(
    val ingredientId: Int,
    val lunchId: Int,
    val ingredient: Ingredient
) : Parcelable