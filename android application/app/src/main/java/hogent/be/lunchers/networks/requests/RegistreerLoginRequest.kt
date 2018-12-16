package hogent.be.lunchers.networks.requests

/**
 * Een klasse die geconverteerd kan worden naar JSON. Deze klasse wordt gebruikt als data bij een RegistreerLoginRequest aan de server
 */
data class RegistreerLoginRequest(
    val gebruikersnaam: String,
    val wachtwoord: String,
    val rol: String
)