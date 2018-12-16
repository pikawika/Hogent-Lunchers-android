package hogent.be.lunchers.networks.responses

/**
 * Een klasse die van JSON extracted kan worden. Deze klasse wordt gebruikt als de server een TokenResponse antwoord
 */
data class TokenResponse(
    val token: String
)