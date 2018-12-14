package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.bumptech.glide.Glide.init
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.models.Reservatie
import hogent.be.lunchers.models.ReservatieRepository
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.OrderUtil.convertIntToStatus
import hogent.be.lunchers.utils.OrderUtil.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class OrderViewModel : InjectedViewModel() {

    @Inject
    lateinit var lunchersApi: LunchersApi

    @Inject
    lateinit var orderRepo: ReservatieRepository

    private val _reservations = MutableLiveData<List<Reservatie>>()

    val reservations: MutableLiveData<List<Reservatie>>
        get() = _reservations

    private var _selectedOrder = MutableLiveData<Reservatie>()

    val selectedOrder: MutableLiveData<Reservatie>
        get() = _selectedOrder

    private var _roomOrders: LiveData<List<Reservatie>>

    val roomOrders: LiveData<List<Reservatie>>
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

    fun setSelectedOrder(orderId: Int) { _selectedOrder.value =  _reservations.value!!.firstOrNull { it.reservatieId == orderId } }

    fun setReservations(reservations: List<Reservatie>) { _reservations.value = reservations }

    private fun onRetrieveAllReservationsSuccess(result: List<Reservatie>) {
        setReservations(result)
        doAsync { orderRepo.insert(result) }
    }

    private fun onRetrieveError() { MessageUtil.showToast("Er is een fout opgetreden tijdens het ophalen van de reservaties van het internet.") }

    fun formatDateSelectedOrder(): String { return formatDate(_selectedOrder.value!!.datum) }

    fun formatStatusSelectedOrder(): String { return convertIntToStatus(_selectedOrder.value!!.status) }

}
