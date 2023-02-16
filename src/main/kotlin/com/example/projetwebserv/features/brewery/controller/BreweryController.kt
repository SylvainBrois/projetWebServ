package com.example.projetwebserv.features.brewery.controller

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.beer.dao.entity.CreateBeer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.entity.CreateBrewery
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("brewery")
class BreweryController() {
    @Autowired
    lateinit var breweryRepository: BreweryRepository

    @GetMapping
    fun findAll(): Iterable<Brewery> =
        breweryRepository.findAll()

    @Operation(description = "Creates a new brewery in the DB")
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Created successfully",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Brewery::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad request"
        ),
        ApiResponse(
            responseCode = "500",
            description = "Server error"
        )
    )
    @PostMapping
    fun create(@RequestBody createBrewery: CreateBrewery): ResponseEntity<Any>{

        return ResponseEntity.status(HttpStatus.CREATED).body(
            breweryRepository.save(
                Brewery(
                    name = createBrewery.name,
                    city = createBrewery.city,
                    state = createBrewery.state
                )
            )
        )
    }


    @Operation(summary = "Gets a brewery by its ID")
    @Parameter(
        `in` = ParameterIn.QUERY,
        name = "id",
        description = "Id of the brewery you want to get"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Beer::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        example = "{\"brewery\" : \"not found\"}")
                )
            ]
        )
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val brewery : Brewery = breweryRepository.findById(id).orElse(null)
            ?: return ResponseEntity(hashMapOf<String,String>(Pair("brewery","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(brewery)
    }

    @Operation(summary = "Deletes a beer by its ID")
    @Parameter(
        `in` = ParameterIn.QUERY,
        name = "id",
        description = "Id of the beer you want to deletes"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Beer::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        example = "{\"brewery\" : \"not found\"}")
                )
            ]
        )
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        val brewery : Brewery = breweryRepository.findById(id).orElse(null)
            ?: return ResponseEntity(hashMapOf<String,String>(Pair("brewery","not found")), HttpStatus.NOT_FOUND)
        try {
            breweryRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) { }

        return ResponseEntity.ok(brewery)
    }

    @Operation(summary = "Modifies a brewery by its ID")
    @Parameter(
        `in` = ParameterIn.QUERY,
        name = "id",
        description = "Id of the brewery you want to modify"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Brewery::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "304",
            description = "Not modified",
            content =[
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        example = "{\"brewery\" : \"not modified\"}"
                    )
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        example = "{\"brewery\" : \"not found\"}")
                )
            ]
        )
    )
    @PutMapping("/{id}",consumes = ["application/json"])
    fun update(@PathVariable id: Long,@RequestBody data: CreateBrewery): ResponseEntity<Any> {
        val brewery : Brewery = breweryRepository.findById(id).orElse(null)
            ?: return ResponseEntity(hashMapOf<String,String>(Pair("brewery","not found")), HttpStatus.NOT_FOUND)
        try {
            return ResponseEntity.ok(breweryRepository.save(
                Brewery(
                id = id,
                name = data.name,
                city = data.city,
                state = data.state,
            )
            ))
        } catch (e : Exception){
            return ResponseEntity(hashMapOf<String,String>(Pair("beer","not updated")), HttpStatus.NOT_MODIFIED)
        }
    }
}
