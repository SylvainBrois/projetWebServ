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
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
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
    @GetMapping
    fun findAll(): Iterable<Beer> =
            beerRepository.findAll()

    @Operation(summary = "Gets a beer by its ID")
    @Parameter(
        `in` = ParameterIn.QUERY,
        name = "id",
        description = "Id of the beer you want to get"
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
                        example = "{\"beer\" : \"not found\"}")
                )
            ]
        )
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val beer : Beer? = beerRepository.findById(id).orElse(null)
        if (beer==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("beer","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(beer)
    }


    @Operation(summary = "Creates a new beer in the DB")
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Created successfully",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Beer::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad request"
        ),
        ApiResponse(
            responseCode = "500",
            description = "Server error",
        )
    )
    @PostMapping
    fun create(@RequestBody createBeer: CreateBeer): ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.CREATED).body(
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
        )
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
                        example = "{\"beer\" : \"not found\"}")
                )
            ]
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

    @Operation(summary = "Modifies a beer by its ID")
    @Parameter(
        `in` = ParameterIn.QUERY,
        name = "id",
        description = "Id of the beer you want to modify"
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
            responseCode = "304",
            description = "Not modified",
            content =[
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        example = "{\"beer\" : \"not modified\"}"
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
                        example = "{\"beer\" : \"not found\"}")
                )
            ]
        )
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