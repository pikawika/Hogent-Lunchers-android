package hogent.be.lunchers.models

import com.squareup.moshi.Json

data class BlacklistedItem(
    @field:Json(name = "allergyId") val blacklistedItemId: Int,
    @field:Json(name = "allergyNaam") val blacklistedItemName: String)