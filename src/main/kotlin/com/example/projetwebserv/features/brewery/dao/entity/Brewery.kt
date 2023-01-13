package com.example.projetwebserv.features.brewery.dao.entity

import com.example.projetwebserv.features.brewery.model.ViewBrewery
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Brewery(
    @Id
    @GeneratedValue
    val id : Int,
    val name : String,
    val city : String,
    val state : String
)
fun Brewery.toView() = ViewBrewery(id, name, city, state)