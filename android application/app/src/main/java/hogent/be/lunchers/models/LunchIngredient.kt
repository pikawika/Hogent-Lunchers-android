package hogent.be.lunchers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Een [LunchTag] vormt een tussentabel tussen de [Lunch] en de [Tag]
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
@Parcelize
data class LunchIngredient(
    val ingredientId: Int,
    val lunchId: Int,
    val ingredient: Ingredient
) : Parcelable