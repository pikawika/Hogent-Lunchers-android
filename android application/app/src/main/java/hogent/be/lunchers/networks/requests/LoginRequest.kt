package hogent.be.lunchers.networks.requests

/**
 * Een klasse die geconverteerd kan worden naar JSON. Deze klasse wordt gebruikt als data bij een LoginRequest aan de server
 */
data class LoginRequest(
    val gebruikersnaam : String,
    val wachtwoord : String
)