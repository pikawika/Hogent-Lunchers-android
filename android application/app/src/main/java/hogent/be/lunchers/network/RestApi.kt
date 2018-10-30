package hogent.be.lunchers.network

import hogent.be.lunchers.models.Lunch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface RestApi {

    @GET("lunches")
    fun getLunches(): Call<List<Lunch>>

    companion object Factory {
        private const val BASE_URL = "https://www.lunchers.be/"
        fun create(): RestApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(RestApi::class.java)
        }
    }
}