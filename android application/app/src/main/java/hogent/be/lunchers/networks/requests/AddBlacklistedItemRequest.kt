package hogent.be.lunchers.networks.requests

/**
 * Een klasse die geconverteerd kan worden naar JSON. Deze klasse wordt gebruikt als data bij een AddBlacklistedItemRequest aan de server
 */
data class AddBlacklistedItemRequest(
    val allergie : String
)