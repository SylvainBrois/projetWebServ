package com.example.projetwebserv.features.beer.dao.entity
import com.example.projetwebserv.features.beer.model.ViewBeer;
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Beer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,
    val abv : Float,
    val ibu : Float?,
    val name : String,
    val style : String,
    val breweryId : Int,
    val ounces : Float
)

fun Beer.toView() = ViewBeer(id, abv, ibu, name, style, breweryId, ounces)
   