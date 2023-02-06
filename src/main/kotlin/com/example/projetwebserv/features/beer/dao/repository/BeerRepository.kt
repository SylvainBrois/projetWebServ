package com.example.projetwebserv.features.brewery.dao.repository

import com.example.projetwebserv.features.beer.dao.entity.Beer
import org.springframework.data.repository.CrudRepository

interface BeerRepository : CrudRepository<Beer, Long> {
}