package hogent.be.lunchers.networks.requests

data class LoginRequest(
    val gebruikersnaam : String,
    val wachtwoord : String
)