package hogent.be.lunchers.networks.requests

/**
 * Een klasse die geconverteerd kan worden naar JSON. Deze klasse wordt gebruikt als data bij een ReservatieRequest aan de server
 */
data class ReservatieRequest(
    val lunchId: Int,
    val aantal: Int,
    val datum: String,
    val opmerking: String
)