package com.example.projetwebserv.features.beer.model


data class ViewBeer(
    val id : Int,
    val abv : Float,
    val ibu : Float?,
    val name : String,
    val style : String,
    val breweryId : Int,
    var ounces : Float
    )