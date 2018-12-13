package com.lennertbontinck.carmeetsandroidapp.enums

/**
 * Een helpende enum klasse om een lijstdesign toe te kennen.
 * Elk enumitem heeft een *layoutId* (Int) waarde die overeenstemt met de id van de bijhorende xml die een recyclerviewitem voorstelt.
 * */
enum class FilterEnum(val filterManier: Int) {
    PRICEHIGHEST(0),
    PRICELOWEST(1),
    DISTANCE(2),
    RECENT(3)
}