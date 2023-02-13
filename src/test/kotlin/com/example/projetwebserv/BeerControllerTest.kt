package com.example.projetwebserv

import com.example.projetwebserv.features.beer.dao.entity.CreateBeer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
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

class BeerControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper
    @Autowired
    lateinit var breweryRepository: BreweryRepository

    @Test
    @Order(1)
    internal fun setUp() {
        breweryRepository.save(
            Brewery(
                name = "name",
                city = "city",
                state = "state"
            )
        )
    }


    @Test
    @Order(2)
    fun emptyBeers() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/beer")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("[]"))
            .andReturn()
    }


    @Test
    @Order(3)
    fun postBeersOK() {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/beer")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(CreateBeer(1.5f, 1.5f, "name","style",1,1.5f)))
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.abv").value(1.5f))
            .andExpect(jsonPath("$.ibu").value(1.5f))
            .andExpect(jsonPath("$.name").value("name"))
            .andExpect(jsonPath("$.style").value("style"))
            .andExpect(jsonPath("$.brewery").value(1))
            .andExpect(jsonPath("$.ounces").value(1.5f))
            .andReturn();
    }



    @Test
    @Order(4)
    fun GetBeerById() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/beer/1")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.abv").value(1.5f))
            .andExpect(jsonPath("$.ibu").value(1.5f))
            .andExpect(jsonPath("$.name").value("name"))
            .andExpect(jsonPath("$.style").value("style"))
            .andExpect(jsonPath("$.brewery").value(1))
            .andExpect(jsonPath("$.ounces").value(1.5f))
            .andReturn();
    }

    @Test
    @Order(5)
    fun UpdateBeerbyId() {
        mockMvc.perform(MockMvcRequestBuilders
            .put("/beer/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(CreateBeer(1.5f, 1.5f, "nameuh","styleuh",1,1.5f)))
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.abv").value(1.5f))
            .andExpect(jsonPath("$.ibu").value(1.5f))
            .andExpect(jsonPath("$.name").value("nameuh"))
            .andExpect(jsonPath("$.style").value("styleuh"))
            .andExpect(jsonPath("$.brewery").value(1))
            .andExpect(jsonPath("$.ounces").value(1.5f))
            .andReturn();
    }

    @Test
    @Order(6)
    fun DeleteBeerbyId() {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/beer/1")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.abv").value(1.5f))
            .andExpect(jsonPath("$.ibu").value(1.5f))
            .andExpect(jsonPath("$.name").value("nameuh"))
            .andExpect(jsonPath("$.style").value("styleuh"))
            .andExpect(jsonPath("$.brewery").value(1))
            .andExpect(jsonPath("$.ounces").value(1.5f))
            .andReturn();
    }
}
