package hogent.be.lunchers.networks

import hogent.be.lunchers.models.Lunch
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Een *interface* die de methoden voorziet om objecten van de *backend server* te halen.
 */
interface LunchersApi {

    /**
     * Haal alle lunches op
     */
    //voorbeeld url: https://lunchers.ml/api/lunch
    @GET("api/lunch")
    fun getAllLunches(): Observable<List<Lunch>>
}