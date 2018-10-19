package hogent.be.lunchers.model

import java.util.*

data class Lunch(val lunchId: Int, val naam: String, val prijs: Double, val beschrijving: String, val afbeeldingen: List<Int>, val ingredienten: List<String>, val beginDatum: Date, val eindDatum: Date, val tags: List<Tag>)