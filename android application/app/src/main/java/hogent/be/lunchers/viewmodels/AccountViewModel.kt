package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.networks.responses.TokenResponse
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.networks.requests.LoginRequest
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Een [InjectedViewModel] klasse die alle lunches bevat.
 */
class AccountViewModel : InjectedViewModel() {

    /**
     * De lijst van alle lunches zoals die van de server gehaald is
     */
    private var gebruikersnaam = MutableLiveData<String>()

    /**
     * De lijst van alle lunches zoals die van de server gehaald is
     */
    private var token = MutableLiveData<String>()

    /**
     * Bool of je al dan niet aangemeld bent
     */
    private var aangemeld = MutableLiveData<Boolean>()

    /**
     * een instantie van de lunchersApi om data van de server op te halen
     */
    @Inject
    lateinit var lunchersApi: LunchersApi

    /**
     * De subscription voor het login verzoek
     */
    private lateinit var loginSubscription: Disposable

    /**
     * De subscription voor het login verzoek
     */
    private lateinit var registreerSubscription: Disposable



    init {
        token.value = PreferenceUtil().getToken()
        gebruikersnaam.value = PreferenceUtil().getGebruikersnaam()
        aangemeld.value = token.value != ""
    }

    /**
     * Functie voor het behandelen van het mislukken van het ophalen van data van de server
     */
    private fun onRetrieveError(error: Throwable) {
        MessageUtil.showToast(error.message.toString())
    }

    /**
     * Functie voor het behandelen van het starten van een rest api call
     *
     * Zal een loading fragment tonen of dergelijke
     */
    private fun onRetrieveStart() {
        //hier begint api call
        //nog een soort loading voozien
    }

    /**
     * Functie voor het behandelen van het eindigen van een rest api call
     *
     * Sluit het loading fragment of dergelijke
     */
    private fun onRetrieveFinish() {
        //hier eindigt api call
        //de loading hier nog stoppen
    }

    /**
     * Functie voor het behandelen van het succesvol ophalen van de meetings
     *
     * Zal de lijst van meetings gelijkstellen met het results
     */
    private fun onRetrieveLoginSuccess(result: TokenResponse) {
        token.value = result.token
        PreferenceUtil().setToken(result.token)
        aangemeld.value = true
    }

    /**
     * Disposed alle subscriptions wanneer de [LunchViewModel] niet meer gebruikt wordt.
     */
    override fun onCleared() {
        super.onCleared()
        loginSubscription.dispose()
        registreerSubscription.dispose()
    }

    /**
     * returnt de lijst van alle lunches als MutableLiveData
     */
    fun login(gebruikersnaam : String, wachtwoord : String): Boolean {
        loginSubscription = lunchersApi.login(LoginRequest(gebruikersnaam, wachtwoord))
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveLoginSuccess(result) },
                { error -> onRetrieveError(error) }
            )
        return aangemeld.value!!
    }

}