package hogent.be.lunchers.utils

import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.models.Location

/**
 * Een util voor het helpen met omzetten van data naar een string in een bepaald formaat
 */
object StringFormattingUtil {
    /**
     * zet een [Location] object om naar een duidelijke string als:
     * straat huisnr, postcode gemeente
     */
    @JvmStatic
    fun locationToString(location: Location): String {
        return location.street + " " + location.houseNumber + ", " + location.postalCode + " " + location.city
    }

    /**
     * zet een int om naar een zin hoeveel mensen.
     */
    @JvmStatic
    fun amountOfPeopleToString(amount: Int): String {
        return MainActivity.getContext().getString(R.string.text_amount) + ": " + amount + " " +
                (if (amount == 1) MainActivity.getContext().getString(R.string.text_person_lowercase)
                else MainActivity.getContext().getString(R.string.text_persons_lowercase))
    }
}