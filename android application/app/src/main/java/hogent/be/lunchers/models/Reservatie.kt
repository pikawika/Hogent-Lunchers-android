package hogent.be.lunchers.models

import java.util.*

data class Reservatie(val reservatieId: Int, val gebruikersId: Int, val lunchId: Int, val datum: Date)