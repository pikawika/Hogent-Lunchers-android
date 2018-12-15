package hogent.be.lunchers.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hogent.be.lunchers.models.Lunch
import java.util.*

class RoomConverters {

    // Een Long terug omzetten naar een date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? { return value?.let { Date(it) } }

    // Een date omzetten naar een Long
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? { return date?.time }

    // Een lunch omzetten naar JSON formaat
    @TypeConverter
    fun fromLunchJson(value: Lunch): String {
        return Gson().toJson(value)
    }

    // JSON terug omzetten naar een lunch
    @TypeConverter
    fun jsonToLunch(lunch: String): Lunch {
        val type = object : TypeToken<Lunch>() {}.type
        return Gson().fromJson<Lunch>(lunch, type)
    }

}