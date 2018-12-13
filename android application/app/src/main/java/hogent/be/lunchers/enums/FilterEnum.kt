package com.lennertbontinck.carmeetsandroidapp.enums

/**
 * Een helpende enum klasse om een lijstdesign toe te kennen.
 * Elk enumitem heeft een *layoutId* (Int) waarde die overeenstemt met de id van de bijhorende xml die een recyclerviewitem voorstelt.
 * */
enum class FilterEnum(val filterManier: Int) {

    RECENT(0),
    PRICEHIGHEST(1),
    PRICELOWEST(2),
    DISTANCE(3)

}