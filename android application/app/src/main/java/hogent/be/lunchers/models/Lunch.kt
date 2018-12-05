package hogent.be.lunchers.models

import android.databinding.BindingAdapter
import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.moshi.Json
import hogent.be.lunchers.constants.BASE_URL_BACKEND
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Lunch(
    val lunchId: Int,
    val naam: String,
    val prijs: Double,
    val lunchIngredienten: List<LunchIngredient>,
    val beschrijving: String,
    val afbeeldingen: List<Afbeelding>,
    val beginDatum: Date,
    val eindDatum: Date,
    val lunchTags: List<LunchTag>,
    val handelaar: Handelaar
) : Parcelable


@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    if (url != null)
    {
        Glide.with(view.context).load(BASE_URL_BACKEND + url).into(view)
    }
}