package hogent.be.lunchers.utils

import android.app.AlertDialog
import android.content.Context
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import hogent.be.lunchers.activities.MainActivity

/**
 * Een util om je te helpen met het weergeven van *berichten* aan de gebruiker.
 */
object MessageUtil {
    /**
     * Toont een toast op het scherm. Context is voorzien door de [MainActivity]
     *
     * @param message : Het message dat weergegeven moet worden. Required of type String
     *
     * @param lengthOnScreen : Hoe lang de toast op het scherm moet blijven. Optional of type Int (Toast Length), default Toast.LENGTH_LONG.
     *
     */
    @JvmStatic
    fun showToast(message: String, lengthOnScreen: Int = Toast.LENGTH_LONG) {
        Toast.makeText(MainActivity.getContext(), message, lengthOnScreen).show()
    }

    /**
     * Toont een dialoog popup met een textinput en voert met de opgeleverde string een gegeven functie uit
     *
     * @param context : context van het huidige omgeving (niet de [MainActivity] !)
     *
     * @param title : de gewenste titel van de popup
     *
     * @param message : de gewenste omschrijving in de popup
     *
     * @param hint : de gewenste hint in het inputveld
     *
     * @param func : een funtie dat moet uitgevoerd worden met de opgeleverde string
     */
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