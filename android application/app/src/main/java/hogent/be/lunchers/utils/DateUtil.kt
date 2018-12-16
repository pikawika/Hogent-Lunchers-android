package hogent.be.lunchers.utils

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Een util om je te helpen werken met datums.
 */
object DateUtil{
    /**
     * Zorgt er voor een door de backend verstaanbare datum als [String]
     *
     * @param year het jaar van de datum (yyyy), required van type [Int]
     * @param month de maand van de datum (startend bij 1), required van type [Int]
     * @param day de dag in de maand (startend bij 1), required van type [Int]
     * @param hour het uur (24u), required van type [Int]
     * @param minute de minuut, required van type [Int]
     */
    @JvmStatic
    fun formatDateForJson(year: Int, month: Int, day: Int, hour: Int, minute: Int): String{
        return year.toString() + "-" + DecimalFormat("00").format(month).toString() + "-" + DecimalFormat("00").format(day).toString() + "T" + DecimalFormat("00").format(hour).toString() + ":" + DecimalFormat("00").format(minute).toString() + ":00"
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