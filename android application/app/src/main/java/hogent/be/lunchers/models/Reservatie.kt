package hogent.be.lunchers.models

import java.util.*

data class Reservatie(val reservatieId: Int, val aantal: Int, val lunch: Lunch, val datum: Date, val status: Status)