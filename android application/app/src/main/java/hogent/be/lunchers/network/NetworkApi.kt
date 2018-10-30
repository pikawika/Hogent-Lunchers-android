package hogent.be.lunchers.network

import hogent.be.lunchers.models.Lunch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface NetworkApi {

    @GET("lunches")
    fun getAllLunches(): Call<List<Lunch>>

    companion object Factory {
        private const val BASE_URL = "https://lunchers.be/"
        fun create(): NetworkApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(NetworkApi::class.java)
        }
    }
}