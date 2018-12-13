package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.lennertbontinck.carmeetsandroidapp.enums.FilterEnum
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.models.*
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.utils.SearchUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Een [InjectedViewModel] klasse die alle lunches bevat.
 */
class LunchViewModel : InjectedViewModel() {

    /**
     * De lijst van alle lunches die voldoen aan de zoekfilter
     */
    private val filteredLunches = MutableLiveData<List<Lunch>>()

    /**
     * De geselecteerde lunch
     */
    private val selectedLunch = MutableLiveData<Lunch>()

    /**
     * De lijst van alle lunches zoals die van de server gehaald is
     */
    private var allLunches = listOf<Lunch>()

    /**
     * De geselecteerde filter methode
     */
    private var selectedFilter : FilterEnum

    /**
     * een instantie van de lunchersApi om data van de server op te halen
     */
    @Inject
    lateinit var lunchersApi: LunchersApi

    /**
     * De subscription op het get all lunches verzoek
     */
    private var getAllLunchesSubscription: Disposable

    init {
        //initieel vullen met een lege lijst zodat dit niet nul os
        filteredLunches.value = emptyList()
        selectedFilter = PreferenceUtil().getDefaultFilterMethod()
        getAllLunchesSubscription = lunchersApi.getAllLunches()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveAllLunchesSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }



    /**
     * Functie voor het behandelen van het mislukken van het ophalen van data van de server
     */
    private fun onRetrieveError(error: Throwable) {
        //voorlopig harde crash, niet goed want throwt dus bij gewoon geen internet etc
        MessageUtil.showToast("Er is iets foutgegaan met het ophalen van de data")
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
    private fun onRetrieveAllLunchesSuccess(result: List<Lunch>) {
        allLunches = SearchUtil().filterLunch(selectedFilter, result)
        filteredLunches.value = SearchUtil().filterLunch(selectedFilter, result)
        //filteredLunches.value = SearchUtil().filterLunch(selectedFilter, result)
    }

    /**
     * Disposed alle subscriptions wanneer de [LunchViewModel] niet meer gebruikt wordt.
     */
    override fun onCleared() {
        super.onCleared()
        getAllLunchesSubscription.dispose()
    }

    /**
     * returnt de lijst van alle lunches als MutableLiveData
     */
    fun getFilteredLunches(): MutableLiveData<List<Lunch>> {
        return filteredLunches
    }

    /**
     * returnt de lijst van alle lunches als MutableLiveData
     */
    fun getSelectedLunch(): MutableLiveData<Lunch> {
        return selectedLunch
    }

    /**
     * returnt de lijst van alle lunches als MutableLiveData
     */
    fun setSelectedLunch(lunchId: Int) {
        selectedLunch.value = allLunches.firstOrNull { it.lunchId == lunchId }
    }

    /**
     * Resets de gefilterde lunchlist terug naar alle lunches
     */
    fun resetFilteredLunches(){
        filteredLunches.value = allLunches
    }

    /**
    * Lunches opnieuw ophalen om te refreshen
    */
    fun refreshLunches(){
        getAllLunchesSubscription = lunchersApi.getAllLunches()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveAllLunchesSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * Lunches opnieuw ophalen om te refreshen
     */
    fun refreshLunchesFromLocation(latitude: Double, longitude: Double){
        getAllLunchesSubscription = lunchersApi.getAllLunchesFromLocation(latitude, longitude)
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveStart() }
            .doOnTerminate { onRetrieveFinish() }
            .subscribe(
                { result -> onRetrieveAllLunchesSuccess(result) },
                { error -> onRetrieveError(error) }
            )
    }

    /**
     * zoekt met searchstring op naam, beschrijvng, ingredienten en tags
     */
    fun search(searchString:String){
        filteredLunches.value = SearchUtil().searchLunch(searchString, allLunches)
    }

    /**
     * stelt de filtered type in en updat de lijst
     */
    fun setSelectedFilter(filterEnum: FilterEnum){
        selectedFilter = filterEnum
        filteredLunches.value = SearchUtil().filterLunch(selectedFilter, allLunches)
    }

}