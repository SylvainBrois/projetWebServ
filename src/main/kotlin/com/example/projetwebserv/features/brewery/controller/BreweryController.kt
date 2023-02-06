package com.example.projetwebserv.features.brewery.controller

import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.entity.CreateBrewery
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("brewery")
class BreweryController(val breweryRepository: BreweryRepository) {
    @GetMapping
    fun findAll(): Iterable<Brewery> =
        breweryRepository.findAll()

    @GetMapping("/{id}")
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val brewery : Brewery = breweryRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String, String>(Pair("brewery", "not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(brewery)
    }
    @PostMapping
    fun create(@RequestBody createBrewery: CreateBrewery): Brewery =
        breweryRepository.save(
            Brewery(
                name = createBrewery.name,
                city = createBrewery.city,
                state = createBrewery.state
            )
        )

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long):ResponseEntity<Any> {
        val brewery : Brewery = breweryRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String,String>(Pair("brewery","not found")), HttpStatus.NOT_FOUND)
        try {
            breweryRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) { }

        return ResponseEntity.ok(brewery)
    }

    @PutMapping("/{id}",consumes = ["application/json"])
    fun update(@PathVariable id: Long,@RequestBody data: CreateBrewery): ResponseEntity<Any>{
        val brewery : Brewery = breweryRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String,String>(Pair("brewery","not found")), HttpStatus.NOT_FOUND)
        try {
            return ResponseEntity.ok(breweryRepository.save(Brewery(
                    id = id,
                    name = data.name,
                    city = data.city,
                    state = data.state,
            )))
        } catch (e : Exception){
            return ResponseEntity(hashMapOf<String,String>(Pair("brewery","not updated")), HttpStatus.NOT_MODIFIED)
        }
    }

}
