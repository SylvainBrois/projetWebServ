package com.example.projetwebserv.features.brewery.controller

import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.entity.CreateBrewery
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("brewery")
class BreweryController() {
    @Autowired
    lateinit var breweryRepository: BreweryRepository

    @GetMapping
    fun findAll(): Iterable<Brewery> =
        breweryRepository.findAll()

    @PostMapping
    fun create(@RequestBody createBrewery: CreateBrewery): Brewery =
        breweryRepository.save(
            Brewery(
                name = createBrewery.name,
                city = createBrewery.city,
                state = createBrewery.state
            )
        )
}
