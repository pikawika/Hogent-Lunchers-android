package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.Reservatie
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.utils.MessageUtil.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrderViewModel : InjectedViewModel() {

    @Inject
    lateinit var lunchersApi: LunchersApi

    val reservations = MutableLiveData<List<Reservatie>>()

    private val _selectedOrder = MutableLiveData<Reservatie>()

    val selectedOrder: MutableLiveData<Reservatie>
        get() = _selectedOrder

    private var getAllReservationsSubscription: Disposable

    init {
        reservations.value = listOf()

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

    fun setSelectedOrder(orderId: Int) { reservations.value!!.firstOrNull { it.reservatieId == orderId } }

    private fun onRetrieveAllReservationsSuccess(result: List<Reservatie>) { reservations.value = result }

    private fun onRetrieveError() { showToast("Er is een fout opgetreden tijdens het ophalen van de reservaties.") }

}