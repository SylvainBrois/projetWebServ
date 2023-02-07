package com.example.projetwebserv.features.components.populate

import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import com.example.projetwebserv.features.components.populate.parsers.BeerParser
import com.example.projetwebserv.features.components.populate.parsers.BreweryParser
import com.example.projetwebserv.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = ["enabled"],
    havingValue = "true",
    matchIfMissing = true)

@Component
class PopulateRunner(private val taskService: TaskService):CommandLineRunner {
    @Autowired
    private lateinit var beerRepository: BeerRepository
    @Autowired
    private lateinit var resourceLoader: ResourceLoader
    @Autowired
    private lateinit var breweryRepository: BreweryRepository

    override fun run(vararg args: String?) {
        BreweryParser(resourceLoader, breweryRepository).parse("classpath:static/datasets/breweries.csv")
        BeerParser(resourceLoader, beerRepository).parse("classpath:static/datasets/beers.csv")
    }


}