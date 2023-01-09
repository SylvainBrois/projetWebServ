package com.example.projetwebserv.features.brewery.dao.entity

import org.jetbrains.exposed.sql.Table

object Brewery :Table("breweries"){
    val name = varchar("name", 50)
    val city = varchar("city", 50)
    val state = varchar("state", 2)
    val id = integer("id")
}