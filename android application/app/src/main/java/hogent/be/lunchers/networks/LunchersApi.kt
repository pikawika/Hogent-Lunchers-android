package hogent.be.lunchers.networks

import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.networks.requests.LoginRequest
import hogent.be.lunchers.networks.responses.TokenResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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

    /**
     * Login en krijg tokenresponse
     *
     * @param loginRequest een [loginRequest] object van de gebruiker die zich wilt aanmelden
     */
    //voorbeeld url: https://lunchers.ml/api/lunch
    @POST("api/gebruiker/login")
    fun login(@Body loginRequest: LoginRequest): Observable<TokenResponse>



}