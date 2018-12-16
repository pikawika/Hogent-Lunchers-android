package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.constants.ROLE_CUSTOMER
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.models.BlacklistedItem
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.networks.requests.*
import hogent.be.lunchers.networks.responses.BerichtResponse
import hogent.be.lunchers.networks.responses.TokenResponse
import hogent.be.lunchers.repositories.OrderRepository
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Een [InjectedViewModel] klasse die alle info over de aangemelde gebruiker bevat.
 */
class AccountViewModel : InjectedViewModel() {

    /**
     * De orderRepo van de lokale room database
     */
    @Inject
    lateinit var orderRepo: OrderRepository

    /**
     * een instantie van de lunchersApi om data van de server op te halen
     */
    @Inject
    lateinit var lunchersApi: LunchersApi

    /**
     * De gebruikersnaam van de aangemelde user
     */
    var username = MutableLiveData<String>()
        private set

    /**
     * De lijst van blacklisted items van de aangemelde user
     */
    var blacklistedItems = MutableLiveData<List<BlacklistedItem>>()
        private set

    /**
     * Bool of user al dan niet aangemeld is
     */
    var isLoggedIn = MutableLiveData<Boolean>()
        private set

    /**
     * De subscription op blacklisted items verzoeken
     */
    private lateinit var blacklistedItemsSubscription: Disposable

    /**
     * De subscription voor het login verzoek
     */
    private lateinit var loginSubscription: Disposable

    /**
     * De subscription voor het registreer verzoek
     */
    private lateinit var registerSubscription: Disposable

    /**
     * De subscription voor het wijzig wachtwoord
     */
    private lateinit var changePasswordSubscription: Disposable

    init {
        blacklistedItems.value = emptyList()
        username.value = PreferenceUtil.getUsername()
        isLoggedIn.value = PreferenceUtil.getToken() != ""
    }

    /**
     * Logt de gerbuiker in en returnt of al dan niet gelukt
     */
    fun login(username: String, password: String) {
        loginSubscription = lunchersApi.login(LoginRequest(username, password))
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result ->
                    run {
                        onRetrieveLoginSuccess(result)
                        this.username.value = username
                        PreferenceUtil.setUsername(username)
                    }
                },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Haalt de [BlacklistedItem] terug op van de server
     */
    fun refreshBlacklistedItems() {
        blacklistedItemsSubscription = lunchersApi.getAllBlacklistedItems()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveBlacklistedItemsSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Voegt een [BlacklistedItem] toe voor de ingelogde gebruiker
     */
    fun addBlacklistedItem(newItem: String) {
        blacklistedItemsSubscription = lunchersApi.addBlacklistedItem(AddBlacklistedItemRequest(newItem))
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveBlacklistedItemsSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Verwijderd een [BlacklistedItem] van de ingelogde gebruiker
     */
    fun deleteBlacklistedItem(idItemToDelete: Int) {
        blacklistedItemsSubscription = lunchersApi.deleteBlacklistedItem(idItemToDelete)
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveBlacklistedItemsSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }


    /**
     * Veranderd het password van de gebruiker
     */
    fun changePassword(password: String) {
        changePasswordSubscription = lunchersApi.changePassword(WijzigWachtwoordRequest(password))
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveChangePasswordSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Registreert de gebruiker en returnt of al dan niet gelukt
     */
    fun registreerKlant(
        phoneNumber: String,
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String
    ) {
        val registreerLoginRequest = RegistreerLoginRequest(username, password, ROLE_CUSTOMER)
        val registreerGebruikerRequest =
            RegistreerGebruikerRequest(phoneNumber, firstName, lastName, email, registreerLoginRequest)
        registerSubscription = lunchersApi.registreer(registreerGebruikerRequest)
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveRegisterSuccess(result) },
                { error -> onRetrieveError(error) }
            )
        this.username.value = username
        PreferenceUtil.setUsername(username)
    }

    /**
     * meld af door de shared preferences te verwijderen en isLoggedIn te veranderen
     */
    fun logout() {
        PreferenceUtil.deletePreferences()
        doAsync { orderRepo.clearDatabase() }
        isLoggedIn.value = false
    }

    /**
     * returnt boolean of user al dan niet isLoggedIn is
     */
    fun setDefaultFilterMethod(filterEnum: FilterEnum) {
        PreferenceUtil.setDefaultFilterMethod(filterEnum)
    }

    /**
     * returnt boolean of user al dan niet isLoggedIn is
     */
    fun getDefaultBootPage(): PageEnum {
        return PreferenceUtil.getDefaultBootPage()
    }

    /**
     * Stelt de standaard startpagina in
     *
     * @param pageEnum : de pagina die de gebruiker wilt instellen als startpagina
     */
    fun setDefaultBootPage(pageEnum: PageEnum) {
        PreferenceUtil.setDefaultBootPage(pageEnum)
    }

    /**
     * Functie voor het behandelen van het starten van een rest api call
     */
    private fun onRetrieveStart() {
        //hier begint api call
        //nog een soort loading voozien
    }

    /**
     * Functie voor het behandelen van het eindigen van een rest api call
     */
    private fun onRetrieveFinish() {
        //hier eindigt api call
        //de loading hier nog stoppen
    }

    /**
     * Functie voor het behandelen van het mislukken van het ophalen van data van de server
     */
    private fun onRetrieveError(error: Throwable) {
        MessageUtil.showToast("De gevraagde actie is mislukt. " + error.message.toString())
    }

    /**
     * Functie voor het behandelen van het succesvol aanmelden
     *
     * zal token instellen en opslaan, en isLoggedIn in de VM op true zetten
     */
    private fun onRetrieveLoginSuccess(result: TokenResponse) {
        PreferenceUtil.setToken(result.token)
        isLoggedIn.value = true
    }

    /**
     * Functie voor het behandelen van het succesvol registreren
     *
     * zal token instellen en opslaan, en isLoggedIn in de VM op true zetten
     */
    private fun onRetrieveRegisterSuccess(result: TokenResponse) {
        PreferenceUtil.setToken(result.token)
        isLoggedIn.value = true
    }

    /**
     * Functie voor het behandelen van het succesvol wijzigen van een ww
     */
    private fun onRetrieveChangePasswordSuccess(result: BerichtResponse) {
        MessageUtil.showToast(result.bericht)
    }

    /**
     * Functie voor het behandelen van het succesvol ophalen van blacklisted items
     */
    private fun onRetrieveBlacklistedItemsSuccess(result: List<BlacklistedItem>) {
        blacklistedItems.value = result
    }

    /**
     * Disposed alle subscriptions wanneer de [LunchViewModel] niet meer gebruikt wordt.
     */
    override fun onCleared() {
        super.onCleared()
        if (::loginSubscription.isInitialized) {
            loginSubscription.dispose()
        }
        if (::registerSubscription.isInitialized) {
            registerSubscription.dispose()
        }
        if (::blacklistedItemsSubscription.isInitialized) {
            blacklistedItemsSubscription.dispose()
        }
        if (::changePasswordSubscription.isInitialized) {
            blacklistedItemsSubscription.dispose()
        }
    }
}