package com.example.projetwebserv.features.beer.dao.entity

import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

data class CreateBeer(val abv : Float,
                      val ibu : Float?,
                      val name : String,
                      val style : String,
                      val brewery : Long,
                      val ounces : Float) {

}