package hogent.be.lunchers.utils

import android.content.Context
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.constants.*

/**
 * Een util om je te helpen met de shared preferences van de app
 *
 * Shared preferences staan ingesteld op private zijnde dat ze niet toegangelijk zijn vanuit andere applicaties
 */
object PreferenceUtil {
    /**
     * De shared preferences in [Context.MODE_PRIVATE] zodat andere applicaties hier niet aan kunnen
     */
    private val sharedPreferences = MainActivity.getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * Haalt de token op van de shared preferences
     */
    @JvmStatic
    fun getToken() : String? {
        return sharedPreferences.getString(PREFERENCE_TOKEN, "")
    }

    /**
     * Slaat de token op in de shared prefences
     *
     * @param token : de token van de aangemelde gebruiker
     */
    @JvmStatic
    fun setToken(token : String) {
        sharedPreferences.edit().putString(PREFERENCE_TOKEN, token).apply()
    }

    /**
     * Haalt de gebruikersnaam op van de shared preferences
     */
    @JvmStatic
    fun getUsername() : String? {
        return sharedPreferences.getString(PREFERENCE_USERNAME, "")
    }

    /**
     * Slaat de gebruikersnaam op in de shared prefences
     *
     * @param username : de gebruikersnaam van de aangemelde user
     */
    @JvmStatic
    fun setUsername(username : String) {
        sharedPreferences.edit().putString(PREFERENCE_USERNAME, username).apply()
    }

    /**
     * Haalt de standaard filtermethode op van de shared preferences
     */
    @JvmStatic
    fun getDefaultFilterMethod() : FilterEnum {
        return FilterEnum.values()[sharedPreferences.getInt(PREFERENCE_DEFAULTFILTERMETHOD, 0)]
    }

    /**
     * Slaat de standaard filtermethode op in de shared prefences
     *
     * @param filterEnum : de standaard filtermethode als [FilterEnum]
     */
    @JvmStatic
    fun setDefaultFilterMethod(filterEnum: FilterEnum) {
        sharedPreferences.edit().putInt(PREFERENCE_DEFAULTFILTERMETHOD, filterEnum.filter).apply()
    }

    /**
     * Haalt de standaard startpagina op van de shared preferences
     */
    @JvmStatic
    fun getDefaultBootPage() : PageEnum {
        return PageEnum.values()[sharedPreferences.getInt(PREFERENCE_DEFAULTBOOTPAGE, 0)]
    }

    /**
     * Slaat de standaard startpagina op in de shared prefences
     *
     * @param pageEnum : de standaard startpagina als [PageEnum]
     */
    @JvmStatic
    fun setDefaultBootPage(pageEnum: PageEnum) {
        sharedPreferences.edit().putInt(PREFERENCE_DEFAULTBOOTPAGE, pageEnum.page).apply()
    }

    /**
     * Verwijderd alle sharedPreferences
     */
    @JvmStatic
    fun deletePreferences() {
        sharedPreferences.edit().clear().apply()
    }

}