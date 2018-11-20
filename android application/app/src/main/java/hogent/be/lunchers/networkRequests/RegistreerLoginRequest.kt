package hogent.be.lunchers.networkRequests

data class RegistreerLoginRequest(
    val gebruikersnaam: String,
    val wachtwoord: String,
    val rol: String
)