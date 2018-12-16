package hogent.be.lunchers.enums

/**
 * Een helpende enum klasse om een [PageEnum] toe te kennen.
 * Elk enumitem heeft een [page] (Int) waarde die doorheen de app een startpagina representateerd
 * */
enum class PageEnum(val page: Int) {
    LUNCH_LIST(0),
    MAP(1),
    PROFILE(2),
    ORDERS_LIST(3)
}