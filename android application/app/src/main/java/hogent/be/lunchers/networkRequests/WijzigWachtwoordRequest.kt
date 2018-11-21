package hogent.be.lunchers.networkRequests

data class WijzigWachtwoordRequest(
    val gebruikersnaam: String,
    val wachtwoord: String
)