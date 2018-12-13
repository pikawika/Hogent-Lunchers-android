package hogent.be.lunchers.database

import android.arch.persistence.room.*
import android.content.Context
import hogent.be.lunchers.models.Reservatie
import hogent.be.lunchers.utils.RoomConverters

@Database(entities = [Reservatie::class], version = 1)
// Type Converters worden gebruikt om complexe objecten op te kunnen slaan
// in ons geval wordt dit gebruikt voor de datum en de lunch van de reservaties
@TypeConverters(RoomConverters::class)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao() : OrderDao

    companion object {
        private var instance : OrderDatabase? = null

        fun getInstance(context: Context) : OrderDatabase {
            if (instance != null) return instance!!

            val newInstance = Room.databaseBuilder(
                context,
                OrderDatabase::class.java,
                "order_database"
            ).build()

            instance = newInstance

            return newInstance
        }
    }
}