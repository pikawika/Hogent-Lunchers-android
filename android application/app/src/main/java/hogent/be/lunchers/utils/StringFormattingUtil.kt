package hogent.be.lunchers.utils

import hogent.be.lunchers.models.Locatie

object StringFormattingUtil {
    @JvmStatic
    fun locationToString(location: Locatie) : String {
        return location.straat + " " + location.huisnummer + ", " + location.postcode + " " + location.gemeente
    }

    @JvmStatic
    fun amountOfPeopleToString(amount: Int) : String {
        return "Aantal: " + amount + (if (amount == 1) " persoon" else " personen")
    }
}