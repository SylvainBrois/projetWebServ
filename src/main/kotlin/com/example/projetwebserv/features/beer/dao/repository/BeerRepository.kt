package com.example.projetwebserv.features.brewery.dao.repository

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BeerRepository : JpaRepository<Beer, Long> {
}