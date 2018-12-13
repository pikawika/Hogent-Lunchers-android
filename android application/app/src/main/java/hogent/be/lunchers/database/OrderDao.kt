package hogent.be.lunchers.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import hogent.be.lunchers.models.Reservatie

@Dao
interface OrderDao {

    @Insert
    fun insert(orders: List<Reservatie>)

    @Query("SELECT * FROM order_table")
    fun getAllOrders(): LiveData<List<Reservatie>>

    @Query("DELETE FROM order_table")
    fun deleteAllOrders()
}