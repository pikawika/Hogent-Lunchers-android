package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.constants.ROLE_CUSTOMER
import hogent.be.lunchers.models.BlacklistedItem
import hogent.be.lunchers.repositories.ReservatieRepository
import hogent.be.lunchers.networks.responses.TokenResponse
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.networks.requests.*
import hogent.be.lunchers.networks.responses.BerichtResponse
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Een [InjectedViewModel] klasse die alle lunches bevat.
 */
class AccountViewModel : InjectedViewModel() {

    @Inject
    lateinit var orderRepo: ReservatieRepository

    /**
     * De gebruikersnaam van de aangemelde user
     */
    private var gebruikersnaam = MutableLiveData<String>()

    /**
     * De lijst van blacklisted items van de aangemelde user
     */
    private var blacklistedItems = MutableLiveData<List<BlacklistedItem>>()

    /**
     * De subscription op blacklisted items verzoeken
     */
    private lateinit var blacklistedItemsSubscription: Disposable

    /**
     * De token van de aangemelde user
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

    /**
     * De subscription voor het wijzig wachtwoord
     */
    private lateinit var wijzigWachtwoordSubscription: Disposable


    init {
        blacklistedItems.value = emptyList()
        token.value = PreferenceUtil().getToken()
        gebruikersnaam.value = PreferenceUtil().getGebruikersnaam()
        aangemeld.value = token.value != ""


    }

    /**
     * Functie voor het behandelen van het mislukken van het ophalen van data van de server
     */
    private fun onRetrieveError(error: Throwable) {
        MessageUtil.showToast("De gevraagde actie is mislukt. " + error.message.toString())
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
     * Functie voor het behandelen van het succesvol aanmelden
     *
     * zal token instellen en opslaan, en aangemeld in de VM op true zetten
     */
    private fun onRetrieveLoginSuccess(result: TokenResponse) {
        token.value = result.token
        PreferenceUtil().setToken(result.token)
        aangemeld.value = true
    }

    /**
     * Functie voor het behandelen van het succesvol registreren
     *
     * zal token instellen en opslaan, en aangemeld in de VM op true zetten
     */
    private fun onRetrieveRegistreerSuccess(result: TokenResponse) {
        token.value = result.token
        PreferenceUtil().setToken(result.token)
        aangemeld.value = true
    }

    /**
     * Functie voor het behandelen van het succesvol wijzigen van een ww
     */
    private fun onRetrieveWijzigWachtwoordSuccess(result : BerichtResponse) {
        MessageUtil.showToast(result.bericht)
    }

    /**
     * Disposed alle subscriptions wanneer de [LunchViewModel] niet meer gebruikt wordt.
     */
    override fun onCleared() {
        super.onCleared()
        if (::loginSubscription.isInitialized) {
            loginSubscription.dispose()
        }

        if (::registreerSubscription.isInitialized) {
            registreerSubscription.dispose()
        }
        if (::blacklistedItemsSubscription.isInitialized) {
            blacklistedItemsSubscription.dispose()
        }
    }

    /**
     * Logt de gerbuiker in en returnt of al dan niet gelukt
     */
    fun login(gebruikersnaam: String, wachtwoord: String) {
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
        this.gebruikersnaam.value = gebruikersnaam
        PreferenceUtil().setGebruikersnaam(gebruikersnaam)
    }

    /**
     * Logt de gerbuiker in en returnt of al dan niet gelukt
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
     * Logt de gerbuiker in en returnt of al dan niet gelukt
     */
    private fun addBlacklistedItemProcessing(newItem: String) {
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
     * Logt de gerbuiker in en returnt of al dan niet gelukt
     */
    private fun deleteBlacklistedItemProcessing(idItemToDelete: Int) {
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
     * Functie voor het behandelen van het succesvol ophalen van blacklisted items
     */
    private fun onRetrieveBlacklistedItemsSuccess(result: List<BlacklistedItem>) {
        blacklistedItems.value = result
    }

    /**
     * Veranderd het wachtwoord van de gebruiker
     */
    fun changePassword(wachtwoord: String) {
        wijzigWachtwoordSubscription = lunchersApi.changePassword(WijzigWachtwoordRequest(wachtwoord))
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveWijzigWachtwoordSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * registreert de gebruiker en returnt of al dan niet gelukt
     */
    fun registreerKlant(
        telefoonnummer: String,
        voornaam: String,
        achternaam: String,
        email: String,
        gebruikersnaam: String,
        wachtwoord: String
    ) {
        val registreerLoginRequest = RegistreerLoginRequest(gebruikersnaam, wachtwoord, ROLE_CUSTOMER)
        val registreerGebruikerRequest = RegistreerGebruikerRequest(telefoonnummer, voornaam, achternaam, email, registreerLoginRequest)
        registreerSubscription = lunchersApi.registreer(registreerGebruikerRequest)
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveRegistreerSuccess(result) },
                { error -> onRetrieveError(error) }
            )
        this.gebruikersnaam.value = gebruikersnaam
        PreferenceUtil().setGebruikersnaam(gebruikersnaam)
    }

    /**
     * meld af door token te verwijderen en aangemeld vm te veranderen
     */
    fun afmelden() {
        PreferenceUtil().deletePreferences()
        doAsync { orderRepo.clearDatabase() }
        aangemeld.value = false
    }

    /**
     * returnt boolean of user al dan niet aangemeld is
     */
    fun getIsLoggedIn() : MutableLiveData<Boolean> {
        return aangemeld
    }

    /**
     * returnt boolean of user al dan niet aangemeld is
     */
    fun getGebruikersnaam() : MutableLiveData<String> {
        return gebruikersnaam
    }

    /**
     * returnt boolean of user al dan niet aangemeld is
     */
    fun setDefaultFilterMethod(filterEnum: FilterEnum){
        PreferenceUtil().setDefaultFilterMethod(filterEnum)
    }

    /**
     * returnt boolean of user al dan niet aangemeld is
     */
    fun getDefaultBootPage() : PageEnum {
        return PreferenceUtil().getDefaultBootPage()
    }

    /**
     * returnt boolean of user al dan niet aangemeld is
     */
    fun setDefaultBootPage(pageEnum: PageEnum){
        PreferenceUtil().setDefaultBootPage(pageEnum)
    }

    /**
     * Voegt een [BlacklistedItem] toe
     */
    fun addBlacklistedItem(newItem: String){
        addBlacklistedItemProcessing(newItem)
    }

    /**
     * Verwijderd een [BlacklistedItem]
     */
    fun deleteBlacklistedItem(itemToDeleteId: Int){
        deleteBlacklistedItemProcessing(itemToDeleteId)
    }

    /**
     * Geeft [BlacklistedItem] lijst
     */
    fun getBlacklistedItems() : MutableLiveData<List<BlacklistedItem>>{
        return blacklistedItems
    }



}