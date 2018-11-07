package hogent.be.lunchers.utils

import android.content.Context
import android.widget.Toast

object Utils {

    @JvmStatic
    fun makeToast(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }
}