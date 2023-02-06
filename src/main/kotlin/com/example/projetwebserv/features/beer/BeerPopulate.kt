package com.example.projetwebserv.features.beer

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import com.example.projetwebserv.service.TaskService
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = ["enabled"],
    havingValue = "true",
    matchIfMissing = true)

@Component
class BeerPopulate(private val taskService: TaskService):CommandLineRunner {
    @Autowired
    private lateinit var beerDao: BeerRepository
    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    override fun run(vararg args: String?) {
        taskService.execute("command line runner task");
        println("TODO parse csv")
        val resource: Resource = resourceLoader
            .getResource("src/main/resources/static/datasets/beers.csv")
        val inputStream: InputStream = resource.inputStream
        val reader: Reader = InputStreamReader(inputStream)
        var csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
        val csvParser = CSVParser(reader,csvFormat)

        for (csvRecord in csvParser) {

            try{
                beerDao.save(Beer(0, csvRecord.get("abv").toFloat(),csvRecord.get("ibu").toFloatOrNull(), csvRecord.get("name"),csvRecord.get("style"), Brewery(id = csvRecord.get("brewery_id").toLong()), csvRecord.get("ounces").toFloat()))
            }
            catch (exception: NumberFormatException){
                exception.printStackTrace()
            }
        }
    }


}