package hogent.be.lunchers.networks

import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.networks.requests.LoginRequest
import hogent.be.lunchers.networks.requests.RegistreerGebruikerRequest
import hogent.be.lunchers.networks.requests.WijzigWachtwoordRequest
import hogent.be.lunchers.networks.responses.BerichtResponse
import hogent.be.lunchers.networks.responses.TokenResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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



}