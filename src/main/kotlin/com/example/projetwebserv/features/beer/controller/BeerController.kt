package com.example.projetwebserv.features.beer.controller

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.beer.dao.entity.CreateBeer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("beer")
class BeerController(val beerRepository: BeerRepository) {
    @GetMapping
    fun findAll(): Iterable<Beer> =
            beerRepository.findAll()

    @PostMapping
    fun create(@RequestBody createBeer: CreateBeer): Beer =
            beerRepository.save(
                    Beer(
                            abv = createBeer.abv,
                            ibu = createBeer.ibu,
                            name = createBeer.name,
                            style = createBeer.style,
                            _brewery = Brewery(id = createBeer.brewery),
                            ounces = createBeer.ounces
                    )
            )
}