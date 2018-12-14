package hogent.be.lunchers.utils

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.util.Log
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.activities.MainActivity
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class PreferenceUtil() {

    val PREFERENCES_NAME = "lunchersPreferences"
    val PREFERENCE_TOKEN = "lunchersToken"
    val PREFERENCE_GEBRUIKERSNAAM = "lunchersGebruikersnaam"
    val PREFERENCE_DEFAULTBOOTPAGE = "lunchersDefaultBootPage"
    val PREFERENCE_DEFAULTFILTERMETHOD = "lunchersDefaultFilterMethod"
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
        sharedPreferences.edit().putInt(PREFERENCE_DEFAULTBOOTPAGE, 0).apply()
        sharedPreferences.edit().putInt(PREFERENCE_DEFAULTFILTERMETHOD, 0).apply()
    }

    fun getDefaultFilterMethod() : FilterEnum {
        return FilterEnum.values()[sharedPreferences.getInt(PREFERENCE_DEFAULTFILTERMETHOD, 0)]
    }

    fun setDefaultFilterMethod(filterEnum: FilterEnum) {
        sharedPreferences.edit().putInt(PREFERENCE_DEFAULTFILTERMETHOD, filterEnum.filterManier).apply()
    }

    fun getDefaultBootPage() : PageEnum {
        return PageEnum.values()[sharedPreferences.getInt(PREFERENCE_DEFAULTBOOTPAGE, 0)]
    }

    fun setDefaultBootPage(pageEnum: PageEnum) {
        sharedPreferences.edit().putInt(PREFERENCE_DEFAULTBOOTPAGE, pageEnum.page).apply()
    }

}