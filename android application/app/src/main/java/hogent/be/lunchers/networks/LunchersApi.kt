package hogent.be.lunchers.networks

import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.Reservatie
import hogent.be.lunchers.networks.requests.LoginRequest
import hogent.be.lunchers.networks.requests.RegistreerGebruikerRequest
import hogent.be.lunchers.networks.requests.ReservatieRequest
import hogent.be.lunchers.networks.requests.WijzigWachtwoordRequest
import hogent.be.lunchers.networks.responses.BerichtResponse
import hogent.be.lunchers.networks.responses.TokenResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Een *interface* die de methoden voorziet om objecten van de *backend server* te halen.
 */
interface LunchersApi {

    /**
     * Haal alle lunches op
     */
    @GET("api/lunch")
    fun getAllLunches(): Observable<List<Lunch>>

    /**

     * Haal alle lunches op dichts bij locatie
     * https://lunchers.ml/api/lunch?latitude=1&longitude=1
     */
    @GET("api/lunch")
    fun getAllLunchesFromLocation(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Observable<List<Lunch>>

    /*
     * Haal alle reservaties op van de gebruiker
     */
    @GET("api/reservatie")
    fun getAllOrders(): Observable<List<Reservatie>>

    /**
     * Login en return tokenresponse
     *
     * @param loginRequest een [loginRequest] object van de gebruiker die zich wilt aanmelden
     */
    @POST("api/gebruiker/login")
    fun login(@Body loginRequest: LoginRequest): Observable<TokenResponse>

    /**
     * Registreer klant en return tokenresponse
     *
     * @param registreerGebruikerRequest een [RegistreerGebruikerRequest] object van de gebruiker die zich wilt registreren
     */
    @POST("api/gebruiker/registreer")
    fun registreer(@Body registreerGebruikerRequest: RegistreerGebruikerRequest): Observable<TokenResponse>

    /**
     * Wijzigt het wachtwoord van een aangemelde gebruiker.
     *
     * @param wijzigWachtwoordRequest een [WijzigWachtwoordRequest] object met een veld *wachtwoord* voor de gebruiker die
     * zijn wachtwoord wilt wijzigen
     */
    @POST("api/gebruiker//wijzigWachtwoord")
    fun changePassword(@Body wijzigWachtwoordRequest: WijzigWachtwoordRequest) : Observable<BerichtResponse>

    /**
     * Reserveert een lunch
     *
     * @param reservatieRequest een [ReservatieRequest] object met de nodige gegevens voor reservatie
     */
    @POST("api/reservatie")
    fun reserveer(@Body reservatieRequest: ReservatieRequest) : Observable<BerichtResponse>


}