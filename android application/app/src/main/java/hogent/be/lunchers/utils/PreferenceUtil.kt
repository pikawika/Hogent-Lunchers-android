package hogent.be.lunchers.utils

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.util.Log
import hogent.be.lunchers.activities.MainActivity
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class PreferenceUtil() {

    val PREFERENCES_NAME = "lunchersPreferences"
    val PREFERENCE_TOKEN = "lunchersToken"
    val PREFERENCE_GEBRUIKERSNAAM = "lunchersGebruikersnaam"
    private val CONTEXT = MainActivity.getContext()


    private val sharedPreferences = CONTEXT.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getToken() : String {
        return sharedPreferences.getString(PREFERENCE_TOKEN, "")
    }

    fun setToken(token : String) {
        sharedPreferences.edit().putString(PREFERENCE_TOKEN, token).apply()
    }

    fun getGebruikersnaam() : String {
        return sharedPreferences.getString(PREFERENCE_GEBRUIKERSNAAM, "")
    }

    fun setGebruikersnaam(gebruikersnaam : String) {
        sharedPreferences.edit().putString(PREFERENCE_GEBRUIKERSNAAM, gebruikersnaam).apply()
    }

    fun deletePreferences() {
        sharedPreferences.edit().putString(PREFERENCE_TOKEN, "").apply()
        sharedPreferences.edit().putString(PREFERENCE_GEBRUIKERSNAAM, "").apply()
    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTokenId(token:String){
        val data1 = decode(token, DEFAULT)
        var text1: String? = null
        try {
            text1 = String(data1, Charset.defaultCharset())
            Log.e("TOKEN as JSON STRING",text1)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

}