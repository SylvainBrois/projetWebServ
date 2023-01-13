package com.example.projetwebserv.features.brewery.dao.repository

import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import org.springframework.data.repository.CrudRepository

interface BreweryRepository : CrudRepository<Brewery, Long> {
    fun findByNameStartingWith(prefix: String): Iterable<Brewery>

    @org.springframework.data.jpa.repository.Query(
        "SELECT b FROM Brewery b WHERE b.name = :suffix"
    )
    fun search(suffix: String): Iterable<Brewery>
}