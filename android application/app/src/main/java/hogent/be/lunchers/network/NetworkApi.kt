package hogent.be.lunchers.network
import hogent.be.lunchers.models.Lunch
import retrofit2.Call
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import hogent.be.lunchers.networkRequests.LoginRequest
import hogent.be.lunchers.networkRequests.WijzigWachtwoordRequest
import hogent.be.lunchers.networkResponses.TokenResponse
import okhttp3.ResponseBody
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetworkApi {

    @GET("lunch/getall")
    fun getAllLunches(): Call<List<Lunch>>

    @POST("gebruiker/login")
    fun login(@Body loginRequest: LoginRequest): Call<TokenResponse>

    @POST("gebruiker/login")
    fun wijzigWachtwoord(@Header("Authorization") authToken: String, @Body wijzigWachtwoordRequest: WijzigWachtwoordRequest): Call<ResponseBody>

    companion object Factory {
        private const val BASE_URL = "https://www.lunchers.ml/api/"

        private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        fun create(): NetworkApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(NetworkApi::class.java)
        }
    }
}