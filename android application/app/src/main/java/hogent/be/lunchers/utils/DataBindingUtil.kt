package hogent.be.lunchers.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import hogent.be.lunchers.constants.BASE_URL_BACKEND
import android.widget.TextView
import hogent.be.lunchers.models.Locatie
import java.util.*


/**
 * Een util om je te helpen met het weergeven van *berichten* aan de gebruiker.
 */
object DataBindingUtil {
    /**
     * Zorgt er voor dat een android;src bij een imageview gevult wordt adhv glide
     */
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, url: String?) {
        if (url != null)
        {
            Glide.with(view.context).load(BASE_URL_BACKEND + url).into(view)
        }
    }

    /**
     * Zorgt er voor dat een een double mooi wordt weergegeven bij binding naar textview
     */
    @JvmStatic
    @BindingAdapter("android:text")
    fun setDouble(view: TextView, value: Double?) {
        if (value != null && !value.isNaN())
        {
            view.text = String.format("%.2f", value)

        }
    }

    /**
     * Zorgt er voor dat locatie mooi wordt weergegeven
     */
    @JvmStatic
    @BindingAdapter("android:restaurantLocation")
    fun setRestaurantLocation(view: TextView, location: Locatie?) {
        if (location != null)
        {
            view.text = location.straat + " " + location.huisnummer + ", " + location.postcode + " " + location.gemeente
        }
    }

    /**
     * Zorgt er voor dat order datum mooi wordt weergegeven
     */
    @JvmStatic
    @BindingAdapter("android:orderDate")
    fun setOrderDate(view: TextView, date: Date?) {
        if (date != null)
        {
            view.text = "Datum: " + OrderUtil.formatDate(date)
        }
    }

    /**
     * Zorgt er voor dat order amount mooi wordt weergegeven
     */
    @JvmStatic
    @BindingAdapter("android:orderAmount")
    fun setOrderAmount(view: TextView, amount: Int?) {
        if (amount != null)
        {
            view.text = "Aantal: " + amount + " personen"
        }
    }

    /**
     * Zorgt er voor dat order status mooi wordt weergegeven
     */
    @JvmStatic
    @BindingAdapter("android:orderStatus")
    fun setOrderStatus(view: TextView, status: Int?) {
        if (status != null)
        {
            view.text = "Status: " + OrderUtil.convertIntToStatus(status)
        }
    }


}