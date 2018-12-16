package hogent.be.lunchers.utils

import hogent.be.lunchers.activities.MainActivity

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
}