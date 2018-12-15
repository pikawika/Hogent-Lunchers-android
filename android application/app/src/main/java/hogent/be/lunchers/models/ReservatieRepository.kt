package hogent.be.lunchers.models

import hogent.be.lunchers.database.OrderDao

class ReservatieRepository(private val orderDao: OrderDao) {
    val orders = orderDao.getAllOrders()

    fun insert(orders: List<Reservatie>) {
        if (orders.isNotEmpty()) orderDao.deleteAllOrders()
        orders.forEach { orderDao.insert(it) }
    }

    fun clearDatabase() {
        orderDao.deleteAllOrders()
    }
}

