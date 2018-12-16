package hogent.be.lunchers.utils

import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Een util om te helpen met het instellen van GUI elementen
 */
object GuiUtil {
    /**
     * Stelt in dat de gebruiker mag teruggaan en geeft het terug pijltje in de actionbar weer
     *
     * @param mainActivity: een instantie van de [MainActivity] (required)
     */
    @JvmStatic
    fun setCanPop(mainActivity: MainActivity) {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        MainActivity.setCanpop(true)
    }

    /**
     * Stelt in dat de gebruiker niet mag teruggaan en verbergt het terug pijltje in de actionbar
     *
     * @param mainActivity: een instantie van de [MainActivity] (required)
     */
    @JvmStatic
    fun removeCanPop(mainActivity: MainActivity) {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }

    /**
     * Stelt de titel van de actionbar in.
     *
     * @param mainActivity: een instantie van de [MainActivity] (required)
     * @param title: een [String] die de gewenste titel reperesentateerd (optional - default app name)
     */
    @JvmStatic
    fun setActionBarTitle(mainActivity: MainActivity, title: String = MainActivity.getContext().getString(R.string.app_name)) {
        mainActivity.supportActionBar?.title = title
    }

    /**
     * Toont het optiemenu in de actionbar (3 puntjes) met alle filter opties
     */
    @JvmStatic
    fun showFilterMethods(mainActivity: MainActivity) {
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_newest)?.isVisible = true
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_distance)?.isVisible = true
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_highest)?.isVisible = true
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_lowest)?.isVisible = true
    }

    /**
     * verbergt alle filter opties in het optiemenu van de actionbar (3 puntjes)
     */
    @JvmStatic
    fun hideFilterMethods(mainActivity: MainActivity) {
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_newest)?.isVisible = false
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_distance)?.isVisible = false
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_highest)?.isVisible = false
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_lowest)?.isVisible = false
    }
}