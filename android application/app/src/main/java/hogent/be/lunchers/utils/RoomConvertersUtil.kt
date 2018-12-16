package hogent.be.lunchers.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hogent.be.lunchers.models.Lunch
import java.util.*

/**
 * Een util om je te helpen met room
 */
object RoomConvertersUtil {

    /**
     * Een Long terug omzetten naar een date
     */
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Een date omzetten naar een Long
     *
     * @param date : de [Date] die je wenst te converteren
     */
    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    /**
     * Een lunch omzetten naar JSON formaat
     *
     * @param lunch : de [Lunch] die je wenst te converteren
     */
    @TypeConverter
    @JvmStatic
    fun fromLunchJson(lunch: Lunch): String {
        return Gson().toJson(lunch)
    }

    /**
     * JSON terug omzetten naar een lunch
     *
     * @param lunch : de lunch als JSON [String] die je wenst te converteren
     */
    @TypeConverter
    @JvmStatic
    fun jsonToLunch(lunch: String): Lunch {
        val type = object : TypeToken<Lunch>() {}.type
        return Gson().fromJson<Lunch>(lunch, type)
    }

}