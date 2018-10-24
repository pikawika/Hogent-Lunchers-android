package hogent.be.lunchers.model

data class Handelaar(val handelaarsId: Int, val bedrijfsnaam: String, val emailadres: String, val telefoonnummer: String, val adres: String, val latitude: Double, val longitude: Double, val website: String, val lunches: List<Lunch>, val promotieRange: Int)