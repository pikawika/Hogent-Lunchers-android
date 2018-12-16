package hogent.be.lunchers.enums

/**
 * Een helpende enum klasse om een [FilterEnum] toe te kennen.
 * Elk enumitem heeft een [filter] (Int) waarde die doorheen de app een filtermethode representateerd
 * */
enum class FilterEnum(val filter: Int) {
    RECENT(0),
    PRICE_HIGHEST(1),
    PRICE_LOWEST(2),
    DISTANCE(3)
}