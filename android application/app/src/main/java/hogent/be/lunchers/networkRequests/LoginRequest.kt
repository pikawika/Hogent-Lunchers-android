package hogent.be.lunchers.networkRequests

data class LoginRequest(
    val gebruikersnaam: String,
    val wachtwoord: String
)