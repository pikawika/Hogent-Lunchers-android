package hogent.be.lunchers.utils

import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity

/**
 * Een util om je te helpen werken met orders
 */
object OrderUtil {

    /**
     * Converteert de int waarde van de backend zijn enum status naar de bijhorende string
     */
    @JvmStatic
    fun convertIntToStatus(int: Int): String {
        return when (int) {
            0 -> MainActivity.getContext().getString(R.string.text_status_awaited)
            1 -> MainActivity.getContext().getString(R.string.text_status_accepted)
            2 -> MainActivity.getContext().getString(R.string.text_status_declined)
            else -> MainActivity.getContext().getString(R.string.text_status_unknown)
        }
    }


}