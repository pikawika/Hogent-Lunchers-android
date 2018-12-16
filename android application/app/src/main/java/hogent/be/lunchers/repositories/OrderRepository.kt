package hogent.be.lunchers.repositories

import hogent.be.lunchers.database.OrderDao
import hogent.be.lunchers.models.Order

/**
 * Een room repository voor bewerkingen op de lokale opgeslagen orders.
 */
class OrderRepository(private val orderDao: OrderDao) {
    val orders = orderDao.getAllOrders()

    fun insert(orders: List<Order>) {
        if (orders.isNotEmpty()) orderDao.deleteAllOrders()
        orders.forEach { orderDao.insert(it) }
    }

    fun clearDatabase() {
        orderDao.deleteAllOrders()
    }
}

