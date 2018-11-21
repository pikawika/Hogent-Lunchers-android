package hogent.be.lunchers.networkRequests

data class RegistreerRequest(
    val telefoonnummer: String,
    val voornaam: String,
    val achternaam: String,
    val email: String,
    val login: RegistreerLoginRequest
)