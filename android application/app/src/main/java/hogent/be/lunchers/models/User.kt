package hogent.be.lunchers.models

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "gebruikersId") val userId: Int,
    @field:Json(name = "voornaam") val firstName: String,
    @field:Json(name = "achternaam") val lastName: String,
    @field:Json(name = "emailadres") val emailAddress: String,
    @field:Json(name = "telefoonnummer") val phoneNumber: String,
    @field:Json(name = "rol") val role: Int,
    @field:Json(name = "favorieten") val favourites: List<Lunch>,
    @field:Json(name = "reservaties") val reservations: List<Reservation>
)