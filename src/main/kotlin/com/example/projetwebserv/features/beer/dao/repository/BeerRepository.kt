package com.example.projetwebserv.features.brewery.dao.repository

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import org.springframework.data.jpa.repository.JpaRepository


@Repository
interface BeerRepository : JpaRepository<Beer, Long> {
}