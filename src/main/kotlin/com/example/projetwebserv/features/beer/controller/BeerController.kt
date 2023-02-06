package com.example.projetwebserv.features.beer.controller

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.beer.dao.entity.CreateBeer
import com.example.projetwebserv.features.beer.model.ViewBeer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.entity.CreateBrewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import com.example.projetwebserv.features.brewery.model.ViewBrewery
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("beer")
class BeerController(val beerRepository: BeerRepository) {
    @GetMapping
    fun findAll(): Iterable<ViewBeer> =
            beerRepository.findAll().map{ it.toView()}

    @PostMapping
    fun create(@RequestBody createBeer: CreateBeer): ViewBeer =
            beerRepository.save(
                    Beer(
                            abv = createBeer.abv,
                            ibu = createBeer.ibu,
                            name = createBeer.name,
                            style = createBeer.style,
                            brewery = Brewery(id = createBeer.brewery),
                            ounces = createBeer.ounces
                    )
            ).toView()
}