package hogent.be.lunchers.networks.requests

data class ReservatieRequest(
    val lunchId: Int,
    val aantal: Int,
    val datum: String
)