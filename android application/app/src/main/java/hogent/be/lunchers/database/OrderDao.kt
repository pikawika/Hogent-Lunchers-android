package hogent.be.lunchers.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import hogent.be.lunchers.models.Order

/**
 * Een [Dao] object voor de order bewerkingen op de [OrderDatabase]
 */
@Dao
interface OrderDao {

    /**
     * Plaats de lijst van [Order] items in de lokale room database
     *
     * @param[orders] [Order] objecten die in room opgeslagen zullen worden
     */
    @Insert
    fun insert(orders: Order)

    /**
     * Returnt alle [Order] objecten uit de room database
     */
    @Query("SELECT * FROM order_table")
    fun getAllOrders(): LiveData<List<Order>>

    /**
     * Verwijderd alle [Order] objecten uit de room database
     */
    @Query("DELETE FROM order_table")
    fun deleteAllOrders()
}