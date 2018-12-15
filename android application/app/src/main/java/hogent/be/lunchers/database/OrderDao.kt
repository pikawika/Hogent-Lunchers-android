package hogent.be.lunchers.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import hogent.be.lunchers.models.Reservation

@Dao
interface OrderDao {

    @Insert
    fun insert(orders: Reservation)

    @Query("SELECT * FROM order_table")
    fun getAllOrders(): LiveData<List<Reservation>>

    @Query("DELETE FROM order_table")
    fun deleteAllOrders()
}