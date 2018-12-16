package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.models.Order
import hogent.be.lunchers.repositories.OrderRepository
import hogent.be.lunchers.networks.LunchersApi
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

    private val _reservations = MutableLiveData<List<Order>>()

    val reservations: MutableLiveData<List<Order>>
        get() = _reservations


    private var _selectedOrder = MutableLiveData<Order>()

    val selectedOrder: MutableLiveData<Order>
        get() = _selectedOrder

    private var _roomOrders: LiveData<List<Order>>

    val roomOrders: LiveData<List<Order>>
        get() = _roomOrders

    private var getAllReservationsSubscription: Disposable

    init {
        _reservations.value = emptyList()

        _selectedOrder.value = null

        _roomOrders = orderRepo.orders

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
        _selectedOrder.value = null

        getAllReservationsSubscription = lunchersApi.getAllOrders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveAllReservationsSuccess(result) },
                { onRetrieveError() }
            )
    }

    fun setSelectedOrder(orderId: Int) { _selectedOrder.value =  _reservations.value!!.firstOrNull { it.reservationId == orderId } }

    fun setReservations(orders: List<Order>) { _reservations.value = orders }

    private fun onRetrieveAllReservationsSuccess(result: List<Order>) {
        setReservations(result)
        doAsync { orderRepo.insert(result) }
    }

    private fun onRetrieveError() { MessageUtil.showToast("Er is een fout opgetreden tijdens het ophalen van de reservations van het internet.") }

}
