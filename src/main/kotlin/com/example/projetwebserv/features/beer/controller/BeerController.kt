package com.example.projetwebserv.features.beer.controller

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.beer.dao.entity.CreateBeer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("beer")
class BeerController() {
    @Autowired
    lateinit var beerRepository: BeerRepository

    @Operation(summary = "Gets all beers on the table", description = "Returns all the beers if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful"),
        ]
    )
    @GetMapping
    fun findAll(): Iterable<Beer> =
            beerRepository.findAll()

    @Operation(summary = "Gets one beer by its ID on the table")
    @Parameter(`in` = ParameterIn.QUERY,
        name = "id", description = "Id of the beer you want to get")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202",
                description = "Successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Beer::class)
                    )
                ]),
            ApiResponse(responseCode = "404",
                description = "Not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(type = "object",
                        example = "{\"beer\" : \"not found\" }")
                    )
                ]),
            ApiResponse(responseCode = "500",
                description = "Server error"),
        ]
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val beer : Beer? = beerRepository.findById(id).orElse(null)
        if (beer==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("beer","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(beer)
    }
    @Operation(summary = "Adds a new beer to the table")
    @ApiResponses(
            ApiResponse(responseCode = "202", description = "Successful"),
            ApiResponse(responseCode = "400", description = "Invalid Credentials"),
            ApiResponse(responseCode = "500", description = "Server error"),

    )
    @PostMapping()
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

    @Operation(summary = "Deletes a beer by its Id")
    @Parameter(`in` = ParameterIn.QUERY,
        name = "id", description = "Id of the beer you want to delete")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202",
                description = "Successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Beer::class)
                    )
                ]),
            ApiResponse(responseCode = "404",
                description = "Not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = "object",
                            example = "{\"beer\" : \"not found\" }"
                        )
                    )
                ]),
            ApiResponse(responseCode = "500",
                description = "Server error"),
        ]
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

    @Operation(summary = "Modifies a beer by its Id")
    @Parameter(`in` = ParameterIn.QUERY,
        name = "id", description = "Id of the beer you want to modify")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202",
                description = "Successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Beer::class)
                    )
                ]),
            ApiResponse(responseCode = "304",
                description = "Not modified",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(type = "object",
                            example = "{\"beer\" : \"not modified\" }")
                    )
                ]),
            ApiResponse(responseCode = "404",
                description = "Not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(type = "object",
                            example = "{\"beer\" : \"not found\" }")
                    )
                ]),
            ApiResponse(responseCode = "500",
                description = "Server error"),
        ]
    )
    @PutMapping("/{id}",consumes = ["application/json"])
    fun update(@PathVariable id: Long,@RequestBody data:CreateBeer): ResponseEntity<Any>{
        val beer : Beer = beerRepository.findById(id).orElse(null)
                ?: return ResponseEntity(hashMapOf<String,String>(Pair("beer","not found")), HttpStatus.NOT_FOUND)
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
        } catch (e : Exception){
            return ResponseEntity(hashMapOf<String,String>(Pair("beer","not updated")), HttpStatus.NOT_MODIFIED)
        }
    }


}