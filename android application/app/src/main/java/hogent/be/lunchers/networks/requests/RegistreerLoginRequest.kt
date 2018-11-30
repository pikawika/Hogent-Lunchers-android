package hogent.be.lunchers.networks.requests

data class RegistreerLoginRequest(
    val gebruikersnaam: String,
    val wachtwoord: String,
    val rol: String
)