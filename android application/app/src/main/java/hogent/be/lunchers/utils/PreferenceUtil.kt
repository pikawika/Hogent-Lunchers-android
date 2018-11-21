package hogent.be.lunchers.utils

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.util.Log
import com.google.gson.Gson
import hogent.be.lunchers.models.Gebruiker
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser


class PreferenceUtil(context: Context) {

    val PREFERENCES_NAME = "lunchersPreferences"
    val PREFERENCE_TOKEN = "lunchersToken"


    var sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getToken() : String {
        return sharedPreferences.getString(PREFERENCE_TOKEN, "")
    }

    fun setToken(token : String) {
        sharedPreferences.edit().putString(PREFERENCE_TOKEN, token).apply()
    }


    fun deleteToken() {
        sharedPreferences.edit().putString(PREFERENCE_TOKEN, "").apply()
    }

    private fun checkToken(token:String){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTokenId(token:String){
        val data1 = decode(token, DEFAULT)
        var text1: String? = null
        try {
            text1 = String(data1, Charset.defaultCharset())
            val parser = JsonParser()
            Log.e("TOKEN as JSON STRING",text1)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }


    }

}