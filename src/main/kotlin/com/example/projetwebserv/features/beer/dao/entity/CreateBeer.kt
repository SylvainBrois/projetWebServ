package com.example.projetwebserv.features.beer.dao.entity

data class CreateBeer(val abv : Float,
                      val ibu : Float?,
                      val name : String,
                      val style : String,
                      val brewery : Long,
                      val ounces : Float) {

}