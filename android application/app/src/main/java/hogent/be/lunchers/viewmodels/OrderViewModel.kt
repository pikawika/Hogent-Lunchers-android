package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
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

    private val reservations = MutableLiveData<List<Reservatie>>()

    private var getAllReservationsSubscription: Disposable

    init {
        reservations.value = emptyList()

        getAllReservationsSubscription = lunchersApi.getAllOrders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveAllReservationsSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        getAllReservationsSubscription.dispose()
    }

    private fun onRetrieveAllReservationsSuccess(result: List<Reservatie>) { reservations.value = result }

    private fun onRetrieveError(error: Throwable) { showToast("Er is een fout opgetreden tijdens het ophalen van de reservaties.") }

}