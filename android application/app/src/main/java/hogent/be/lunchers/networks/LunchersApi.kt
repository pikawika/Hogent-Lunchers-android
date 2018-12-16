package hogent.be.lunchers.networks

import hogent.be.lunchers.models.BlacklistedItem
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.Order
import hogent.be.lunchers.networks.requests.*
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
    @GET("lunch")
    fun getAllLunches(): Observable<List<Lunch>>

    /**

     * Haal alle lunches op dichts bij location
     * https://lunchers.ml/api/lunch?latitude=1&longitude=1
     */
    @GET("lunch")
    fun getAllLunchesFromLocation(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Observable<List<Lunch>>

    /**
     * Haal alle reservations op van de gebruiker
     */
    @GET("reservatie")
    fun getAllOrders(): Observable<List<Order>>

    /**
     * Login en return tokenresponse
     *
     * @param loginRequest een [loginRequest] object van de gebruiker die zich wilt aanmelden
     */
    @POST("gebruiker/login")
    fun login(@Body loginRequest: LoginRequest): Observable<TokenResponse>

    /**
     * Registreer klant en return tokenresponse
     *
     * @param registreerGebruikerRequest een [RegistreerGebruikerRequest] object van de gebruiker die zich wilt registreren
     */
    @POST("gebruiker/registreer")
    fun registreer(@Body registreerGebruikerRequest: RegistreerGebruikerRequest): Observable<TokenResponse>

    /**
     * Wijzigt het wachtwoord van een aangemelde gebruiker.
     *
     * @param wijzigWachtwoordRequest een [WijzigWachtwoordRequest] object met een veld *wachtwoord* voor de gebruiker die
     * zijn wachtwoord wilt wijzigen
     */
    @POST("gebruiker//wijzigWachtwoord")
    fun changePassword(@Body wijzigWachtwoordRequest: WijzigWachtwoordRequest): Observable<BerichtResponse>

    /**
     * Reserveert een lunch
     *
     * @param reservatieRequest een [ReservatieRequest] object met de nodige gegevens voor reservatie
     */
    @POST("reservatie")
    fun reserveer(@Body reservatieRequest: ReservatieRequest): Observable<BerichtResponse>

    /**
     * Verkrijgt alle blacklisted items van de aangemelde user
     */
    @GET("allergy")
    fun getAllBlacklistedItems(): Observable<List<BlacklistedItem>>

    /**
     * Voegt een blacklisted item toe voor aangemelde user
     *
     * @param addBlacklistedItemRequest een [AddBlacklistedItemRequest] object met een veld *naam* van een
     * zwarte lijst item dat de gebruiker wilt toevoegen
     */
    @POST("allergy")
    fun addBlacklistedItem(@Body addBlacklistedItemRequest: AddBlacklistedItemRequest): Observable<List<BlacklistedItem>>

    /**
     * Delete een blacklisted item voor aangemelde user
     *
     * @param blacklistedItemId een [Int] die de id representeert van het [BlacklistedItem] dat de gebruiker wenst te verwijderen
     */
    @DELETE("allergy/{id}")
    fun deleteBlacklistedItem(@Path("id") blacklistedItemId: Int): Observable<List<BlacklistedItem>>

}