package com.example.projetwebserv.features.brewery.dao.repository

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import org.springframework.data.repository.CrudRepository

interface BeerRepository : CrudRepository<Beer, Long> {
    fun findByNameStartingWith(prefix: String): Iterable<Beer>

    @org.springframework.data.jpa.repository.Query(
        "SELECT b FROM Beer b WHERE b.name LIKE concat('%', :suffix)"
    )
    fun search(suffix: String): Iterable<Brewery>
}