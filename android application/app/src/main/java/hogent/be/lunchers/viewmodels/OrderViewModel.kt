package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.models.Order
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.repositories.OrderRepository
import hogent.be.lunchers.utils.MessageUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class OrderViewModel : InjectedViewModel() {

    @Inject
    lateinit var lunchersApi: LunchersApi

    @Inject
    lateinit var orderRepo: OrderRepository

    val orders = MutableLiveData<List<Order>>()

    var selectedOrder = MutableLiveData<Order>()
        private set

    var roomOrders: LiveData<List<Order>>
        private set

    private var getAllReservationsSubscription: Disposable

    init {
        orders.value = emptyList()

        selectedOrder.value = null

        roomOrders = orderRepo.orders

        getAllReservationsSubscription = lunchersApi.getAllOrders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveAllReservationsSuccess(result) },
                { onRetrieveError() }
            )
    }

    override fun onCleared() {
        super.onCleared()
        getAllReservationsSubscription.dispose()
    }

    fun resetViewModel() {
        selectedOrder.value = null

        getAllReservationsSubscription = lunchersApi.getAllOrders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveAllReservationsSuccess(result) },
                { onRetrieveError() }
            )
    }

    fun setSelectedOrder(orderId: Int) {
        selectedOrder.value = orders.value!!.firstOrNull { it.reservationId == orderId }
    }

    fun setReservations(orders: List<Order>) {
        this.orders.value = orders
    }

    private fun onRetrieveAllReservationsSuccess(result: List<Order>) {
        setReservations(result)
        doAsync { orderRepo.insert(result) }
    }

    private fun onRetrieveError() {
        MessageUtil.showToast(MainActivity.getContext().getString(R.string.error_loading_from_internet))
    }

}
