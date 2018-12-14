package hogent.be.lunchers.enums

/**
 * Een helpende enum klasse om een lijstdesign toe te kennen.
 * Elk enumitem heeft een *layoutId* (Int) waarde die overeenstemt met de id van de bijhorende xml die een recyclerviewitem voorstelt.
 * */
enum class PageEnum(val page: Int) {
    LUNCHLIST(0),
    MAP(1),
    PROFILE(2),
    ORDERSLIST(3)
}