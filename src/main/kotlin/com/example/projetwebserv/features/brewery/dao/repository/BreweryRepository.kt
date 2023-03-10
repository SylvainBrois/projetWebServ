package com.example.projetwebserv.features.brewery.dao.repository

import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface BreweryRepository : JpaRepository<Brewery, Long> {
}