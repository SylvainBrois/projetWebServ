package com.example.projetwebserv

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.entity.CreateBrewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.http.MediaType

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

import com.fasterxml.jackson.databind.ObjectMapper

import org.junit.jupiter.api.*

@ActiveProfiles("test")
@SpringBootTest(properties = arrayOf("command.line.runner.enabled=false"))
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

class BreweryControllerTest {

    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired lateinit var mapper: ObjectMapper
    @Autowired lateinit var beerRepository: BeerRepository


    @Test
    @Order(1)
    fun emptyBreweries() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/brewery")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("[]"))
            .andReturn()
    }

    @Test
    @Order(2)
    fun postBreweryOK() {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/brewery")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(CreateBrewery("name", "city", "state")))
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("name"))
            .andExpect(jsonPath("$.city").value("city"))
            .andExpect(jsonPath("$.state").value("state"))
            .andReturn();
    }

    @Test
    @Order(3)
    fun AddBeer(){
        beerRepository.save(
            Beer(
                abv = 1.5f,
                ibu = 1.5f,
                name = "name",
                style = "style",
                _brewery = Brewery(id = 1),
                ounces = 1.5f
            )
        )
    }

    @Test
    @Order(4)
    fun GetBreweryById() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/brewery/1")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("name"))
            .andExpect(jsonPath("$.city").value("city"))
            .andExpect(jsonPath("$.state").value("state"))
            .andExpect(jsonPath("$.beers").isNotEmpty)
            .andReturn();
    }

    @Test
    @Order(5)
    fun UpdateBrewerybyId() {
        mockMvc.perform(MockMvcRequestBuilders
            .put("/brewery/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(CreateBrewery("nameuh", "kitty", "stare")))
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("nameuh"))
            .andExpect(jsonPath("$.city").value("kitty"))
            .andExpect(jsonPath("$.state").value("stare"))
            .andReturn();
    }

    @Test
    @Order(6)
    fun DeleteBrewerybyId() {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/brewery/1")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("nameuh"))
            .andExpect(jsonPath("$.city").value("kitty"))
            .andExpect(jsonPath("$.state").value("stare"))
            .andReturn();
    }
}
