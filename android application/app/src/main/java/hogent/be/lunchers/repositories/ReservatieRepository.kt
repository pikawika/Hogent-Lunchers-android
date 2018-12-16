package hogent.be.lunchers.repositories

import hogent.be.lunchers.database.OrderDao
import hogent.be.lunchers.models.Order

class ReservatieRepository(private val orderDao: OrderDao) {
    val orders = orderDao.getAllOrders()

    fun insert(orders: List<Order>) {
        if (orders.isNotEmpty()) orderDao.deleteAllOrders()
        orders.forEach { orderDao.insert(it) }
    }

    fun clearDatabase() {
        orderDao.deleteAllOrders()
    }
}

