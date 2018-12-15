package hogent.be.lunchers.utils

import hogent.be.lunchers.models.Location

object StringFormattingUtil {
    @JvmStatic
    fun locationToString(location: Location) : String {
        return location.street + " " + location.houseNumber + ", " + location.postalCode + " " + location.city
    }

    @JvmStatic
    fun amountOfPeopleToString(amount: Int) : String {
        return "Aantal: " + amount + (if (amount == 1) " persoon" else " personen")
    }
}