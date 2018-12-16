package hogent.be.lunchers.networks.requests

/**
 * Een klasse die geconverteerd kan worden naar JSON. Deze klasse wordt gebruikt als data bij een WijzigWachtwoordRequest aan de server
 */
data class WijzigWachtwoordRequest(
    val Wachtwoord : String
)