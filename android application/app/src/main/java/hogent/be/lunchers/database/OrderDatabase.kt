package hogent.be.lunchers.database

import android.arch.persistence.room.*
import android.content.Context
import hogent.be.lunchers.constants.ROOM_ORDER_DATABASE_NAME
import hogent.be.lunchers.models.Order
import hogent.be.lunchers.utils.RoomConverters

/**
 * De database met als entity [Order] en childs
 */
@Database(entities = [Order::class], version = 1)
// Type Converters worden gebruikt om complexe objecten op te kunnen slaan
// in ons geval wordt dit gebruikt voor de date en de lunch van de reservations.
// Momenteel V1 wegens nog niet online staan van app, app zal bij aanpassen model moeten verwijderd worden
// of update methodologie voorzien moeten worden.
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
                ROOM_ORDER_DATABASE_NAME
            ).build()

            instance = newInstance

            return newInstance
        }
    }
}