package hogent.be.lunchers.utils

import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

object GuiUtil {
    @JvmStatic
    fun setCanPop(mainActivity: MainActivity) {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        MainActivity.setCanpop(true)
    }

    @JvmStatic
    fun removeCanPop(mainActivity: MainActivity) {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }

    @JvmStatic
    fun setActionBarTitle(mainActivity: MainActivity, title: String) {
        mainActivity.supportActionBar?.title = title
    }

    @JvmStatic
    fun showFilterMethods(mainActivity: MainActivity) {
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_newest)?.isVisible = true
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_distance)?.isVisible = true
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_highest)?.isVisible = true
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_lowest)?.isVisible = true
    }

    @JvmStatic
    fun hideFilterMethods(mainActivity: MainActivity) {
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_newest)?.isVisible = false
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_distance)?.isVisible = false
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_highest)?.isVisible = false
        mainActivity.toolbar_mainactivity.menu.findItem(R.id.ab_filter_price_lowest)?.isVisible = false
    }
}