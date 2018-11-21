package hogent.be.lunchers.utils

import android.content.Context

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

}