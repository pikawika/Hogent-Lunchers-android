package hogent.be.lunchers.models

data class Gebruiker(val gebruikersId: Int, val voornaam: String, val achternaam: String, val emailadres: String, val telefoonnummer: String, val rol: Int, val favorieten: List<Lunch>, val reservaties: List<Reservatie>)