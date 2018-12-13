package hogent.be.lunchers.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import hogent.be.lunchers.models.Reservatie

@Database(entities = [Reservatie::class], version = 1)
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