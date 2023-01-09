package com.example.projetwebserv.features.beer.dao.entity

import org.jetbrains.exposed.sql.Table

object Beer : Table("beer") {
    val abv = float("abv")
    val ibu = float("ibu").nullable()
    val id = integer("id")
    val name = varchar("name", 50)
    val style = varchar("style", 50)
    val breweryId = integer("breweryId")
    val ounces = varchar("ounces", 50)
}