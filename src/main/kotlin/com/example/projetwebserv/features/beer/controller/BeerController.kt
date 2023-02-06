package com.example.projetwebserv.features.beer.controller

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.beer.dao.entity.CreateBeer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("beer")
class BeerController(val beerRepository: BeerRepository) {
    @GetMapping
    fun findAll(): Iterable<Beer> =
            beerRepository.findAll()

    @GetMapping("/{id}")
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val beer : Beer = beerRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String,String>(Pair("beer","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(beer)
    }

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

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long):ResponseEntity<Any> {
        val beer : Beer = beerRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String,String>(Pair("beer","not found")), HttpStatus.NOT_FOUND)
        try {
            beerRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) { }

        return ResponseEntity.ok(beer)
    }

    @PutMapping("/{id}",consumes = ["application/json"])
    fun update(@PathVariable id: Long,@RequestBody data:CreateBeer): ResponseEntity<Any> {
        val beer: Beer = beerRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String, String>(Pair("beer", "not found")), HttpStatus.NOT_FOUND)
        try {
            return ResponseEntity.ok(beerRepository.save(Beer(
                    id = id,
                    abv = data.abv,
                    ibu = data.ibu,
                    name = data.name,
                    style = data.style,
                    _brewery = Brewery(id = data.brewery),
                    ounces = data.ounces
            )))
        } catch (e: Exception) {
            return ResponseEntity(hashMapOf<String, String>(Pair("beer", "not updated")), HttpStatus.NOT_MODIFIED)
        }
    }

}