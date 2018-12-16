package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.networks.requests.ReservatieRequest
import hogent.be.lunchers.utils.DateUtil
import hogent.be.lunchers.utils.MessageUtil.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Een [InjectedViewModel] klasse die de reservaties behandeld.
 */
class ReservationViewModel : InjectedViewModel() {

    /**
     * een instantie van de lunchersApi om data van de server op te halen
     */
    @Inject
    lateinit var lunchersApi: LunchersApi

    /**
     * De subscription voor het plaatsen van een reservatie
     */
    private lateinit var placeReservationSubscription: Disposable

    /**
     * De geselecteerde selectedLunch
     */
    val selectedLunch = MutableLiveData<Lunch>()

    /**
     * Specifieerd of de selectedLunch al dan niet besteld is
     */
    val reservationPlaced = MutableLiveData<Boolean>()

    /**
     * jaar
     */
    var year: Int = -1

    /**
     * dag
     */
    var day: Int = -1

    /**
     * maand
     */
    var month: Int = -1

    /**
     * uur
     */
    var hour: Int = -1

    /**
     * minuut
     */
    var minute: Int = -1

    /**
     * amount
     */
    var amount: Int = -1

    /**
     * message
     */
    var message: String = ""

    /**
     * Bepaald of de data al dan niet correct is voor de server
     */
    fun valid(): Boolean {
        return year != -1 && month != -1 && day != -1 && hour != -1 && minute != -1 && amount > 0
    }

    /**
     * Plaats de reservatie
     */
    fun placeReservation() {
        placeReservationSubscription = lunchersApi.reserveer(
            ReservatieRequest(
                selectedLunch.value!!.lunchId,
                amount,
                DateUtil.formatDateForJson(year, month, day, hour, minute),
                message
            )
        )
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrievePlaceReservationSuccess() },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Reset de viewmodel zodat een volgende reservatie met een lege model kan beginnen
     */
    fun clear() {
        year = -1
        day = -1
        month = -1
        hour = -1
        minute = -1
        amount = -1
        message = ""
        reservationPlaced.value = false
    }

    /**
     * Stelt de geselecteerde selectedLunch in
     */
    fun setSelectedLunch(lunch: Lunch) {
        this.selectedLunch.value = lunch
    }

    /**
     * Functie voor het behandelen van het mislukken van het ophalen van data van de server
     */
    private fun onRetrieveError(error: Throwable) {
        showToast("Er is een onverwachte fout opgetreden: ${error.message}")
    }

    /**
     * Behandeld wat er gebeurd als de bestelling geplaatst is
     */
    private fun onRetrievePlaceReservationSuccess() {
        reservationPlaced.value = true
    }
}