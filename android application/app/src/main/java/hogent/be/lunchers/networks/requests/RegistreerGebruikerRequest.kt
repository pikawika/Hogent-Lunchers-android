package hogent.be.lunchers.networks.requests

data class RegistreerGebruikerRequest(
    val telefoonnummer: String,
    val voornaam: String,
    val achternaam: String,
    val email: String,
    val login: RegistreerLoginRequest
)