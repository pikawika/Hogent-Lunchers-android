package hogent.be.lunchers.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object OrderUtil {

    @JvmStatic
    fun convertIntToStatus(int: Int): String {
        return when (int) {
            0 -> "In afwachting"
            1 -> "Goedgekeurd"
            2 -> "Afgekeurd"
            else -> "Onbekend"
        }
    }

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date): String {
        val day = SimpleDateFormat("dd/MM/yyy").format(date)
        val format = SimpleDateFormat("HH:mm")
        format.timeZone = TimeZone.getTimeZone("UTC")
        val hour = format.format(date)
        return "$day om $hour"
    }
}