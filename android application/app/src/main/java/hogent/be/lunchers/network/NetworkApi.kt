package hogent.be.lunchers.network

import hogent.be.lunchers.models.Lunch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkApi {

    @GET("lunch/getall")
    fun getAllLunches(): Call<List<Lunch>>

    companion object Factory {
        private const val BASE_URL = "https://lunchers.ml/api/"

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