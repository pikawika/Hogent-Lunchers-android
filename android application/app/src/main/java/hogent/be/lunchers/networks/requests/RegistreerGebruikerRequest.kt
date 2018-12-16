package hogent.be.lunchers.networks.requests

/**
 * Een klasse die geconverteerd kan worden naar JSON. Deze klasse wordt gebruikt als data bij een RegistreerGebruikerRequest aan de server
 */
data class RegistreerGebruikerRequest(
    val telefoonnummer: String,
    val voornaam: String,
    val achternaam: String,
    val email: String,
    val login: RegistreerLoginRequest
)