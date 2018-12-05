package hogent.be.lunchers.networks.requests

import java.util.*

data class ReservatieRequest(
    val lunchId: Int,
    val aantal: Int,
    val datum: Date
)