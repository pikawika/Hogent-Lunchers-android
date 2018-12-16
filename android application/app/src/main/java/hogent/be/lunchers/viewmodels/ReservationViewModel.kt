package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.telephony.euicc.DownloadableSubscription
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.networks.requests.ReservatieRequest
import hogent.be.lunchers.networks.requests.WijzigWachtwoordRequest
import hogent.be.lunchers.networks.responses.BerichtResponse
import hogent.be.lunchers.utils.DateUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.MessageUtil.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat
import java.util.Date
import javax.inject.Inject
import kotlin.math.min

/**
 * Een [InjectedViewModel] klasse die de reservations behandeld.
 */
class ReservationViewModel : InjectedViewModel() {

    /**
     * een instantie van de lunchersApi om data van de server op te halen
     */
    @Inject
    lateinit var lunchersApi: LunchersApi

    /**
     * De subscription voor het login verzoek
     */
    private lateinit var registreerSubscription: Disposable

    /**
     * De geselecteerde lunch
     */
    private val lunch = MutableLiveData<Lunch>()

    /**
     * Specifieerd of de lunch al dan niet gereserveerd is
     */
    private val gereserveerd = MutableLiveData<Boolean>()

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
     * returnt de lijst van alle lunches als MutableLiveData
     */
    fun getSelectedLunch(): MutableLiveData<Lunch> {
        return lunch
    }

    /**
     * returnt de lijst van alle lunches als MutableLiveData
     */
    fun setSelectedLunch(lunch: Lunch) {
        this.lunch.value = lunch
    }

    fun valid() : Boolean{
        return year != -1 && month != -1 && day != -1 && hour != -1 && minute != -1 && amount > 0
    }

    private fun makeJsonDate() : String{
        return DateUtil.formatDateForJson(year,month,day,hour,minute)
    }

    /**
     * Veranderd het wachtwoord van de gebruiker
     */
    fun placeReservation() {
        registreerSubscription = lunchersApi.reserveer(ReservatieRequest(lunch.value!!.lunchId, amount, makeJsonDate(), message))
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveReserveerSuccess() },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Functie voor het behandelen van het mislukken van het ophalen van data van de server
     */
    private fun onRetrieveError(error: Throwable) { showToast("Er is een onverwachte fout opgetreden: ${error.message}") }

    /**
     * Functie voor het behandelen van het succesvol aanmelden
     *
     * zal token instellen en opslaan, en aangemeld in de VM op true zetten
     */
    private fun onRetrieveReserveerSuccess() { gereserveerd.value = true }

    fun getReservationPlaced() : MutableLiveData<Boolean> { return gereserveerd }

    fun clear() {
        year = -1
        day = -1
        month = -1
        hour = -1
        minute = -1
        amount = -1
        message = ""
        gereserveerd.value = false
    }

}