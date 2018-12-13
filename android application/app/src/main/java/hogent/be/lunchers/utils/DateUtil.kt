package hogent.be.lunchers.utils

import java.text.DecimalFormat

class DateUtil{
    fun formatDateForJson(year: Int, month: Int, day: Int, hour: Int, minute: Int): String{
        return year.toString() + "-" + DecimalFormat("00").format(month).toString() + "-" + DecimalFormat("00").format(day).toString() + "T" + DecimalFormat("00").format(hour).toString() + ":" + DecimalFormat("00").format(minute).toString() + ":00"
    }


}