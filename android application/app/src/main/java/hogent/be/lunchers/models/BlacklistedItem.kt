package hogent.be.lunchers.models

import com.squareup.moshi.Json

/**
 * Een [BlacklistedItem] is een item dat ingesteld kan worden door de gebruiker om lijsten te filteren deze niet weer te geven.
 *
 * Filtert lunches weg met voorkomen van [blacklistedItemName] in [Ingredient]en of [Tag]s.
 *
 * Deze klas is Parcelable zodat retrofit deze kan gebruiken
 */
data class BlacklistedItem(
    @field:Json(name = "allergyId") val blacklistedItemId: Int,
    @field:Json(name = "allergyNaam") val blacklistedItemName: String)