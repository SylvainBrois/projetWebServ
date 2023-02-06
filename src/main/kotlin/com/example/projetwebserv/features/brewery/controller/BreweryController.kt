package com.example.projetwebserv.features.brewery.controller

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.entity.CreateBrewery
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import com.example.projetwebserv.features.brewery.model.ViewBrewery
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("brewery")
class BreweryController(val breweryRepository: BreweryRepository) {
    @GetMapping
    fun findAll(): Iterable<ViewBrewery> =
        breweryRepository.findAll().map{ it.toView()}

    @PostMapping
    fun create(@RequestBody createBrewery: CreateBrewery): ViewBrewery =
        breweryRepository.save(
            Brewery(
                name = createBrewery.name,
                city = createBrewery.city,
                state = createBrewery.state
            )
        ).toView()
}
