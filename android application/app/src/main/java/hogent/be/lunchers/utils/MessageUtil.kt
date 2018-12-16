package hogent.be.lunchers.utils

import android.app.AlertDialog
import android.content.Context
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity

/**
 * Een util om je te helpen met het weergeven van *berichten* aan de gebruiker.
 */
object MessageUtil {
    /**
     * Toont een toast op het scherm. Context is voorzien door de [MainActivity]
     *
     * @param[bericht] Het bericht dat weergegeven moet worden. Required of type String
     *
     * @param[tijd] Hoe lang de toast op het scherm moet blijven. Optional of type Int (Toast Length), default Toast.LENGTH_LONG.
     *
     */
    @JvmStatic
    fun showToast(bericht: String, tijd: Int = Toast.LENGTH_LONG) {
        Toast.makeText(MainActivity.getContext(), bericht, tijd).show()
    }

    @JvmStatic
    fun showDialogWithTextInput(context : Context, title: String, message: String, hint: String, func: (String) -> Unit) {
        val editText = EditText(context)
        editText.setSingleLine(false)
        editText.hint = hint
        val container = FrameLayout(context)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.marginStart = 100
        params.marginEnd = 100
        editText.layoutParams = params
        container.addView(editText)

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(container)
            .setMessage(message)
            .setPositiveButton("Voeg toe") { _, _ -> func(editText.text.toString()) }
            .setNeutralButton("Annuleren") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}