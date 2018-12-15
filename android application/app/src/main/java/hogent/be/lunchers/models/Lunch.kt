package hogent.be.lunchers.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Lunch(
    val lunchId: Int,
    @field:Json(name = "naam") val name: String,
    @field:Json(name = "prijs") val price: Double,
    val lunchIngredienten: List<LunchIngredient>,
    @field:Json(name = "beschrijving") val description: String,
    @field:Json(name = "afbeeldingen") val images: List<Image>,
    @field:Json(name = "beginDatum") val startDate: Date,
    @field:Json(name = "eindDatum") val endDate: Date,
    val lunchTags: List<LunchTag>,
    @field:Json(name = "handelaar") val merchant: Merchant
) : Parcelable