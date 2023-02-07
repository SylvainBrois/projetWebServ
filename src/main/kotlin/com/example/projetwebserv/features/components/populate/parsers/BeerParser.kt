package com.example.projetwebserv.features.components.populate.parsers

import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BeerRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
class BeerParser(var resourceLoader: ResourceLoader, var beerRepository: BeerRepository) {

    fun parse(filepath : String) {
        val resource: Resource = resourceLoader
            .getResource(filepath)
        val inputStream: InputStream = resource.inputStream
        val reader: Reader = InputStreamReader(inputStream)
        var csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
        val csvParser = CSVParser(reader,csvFormat)

        for (csvRecord in csvParser) {

            try{
                beerRepository.save(Beer(0,
                    if (csvRecord.get("abv").isEmpty())  0.0f else csvRecord.get("abv").toFloat(),
                    csvRecord.get("ibu").toFloatOrNull(),
                    csvRecord.get("name"),
                    csvRecord.get("style"),
                    Brewery(id = csvRecord.get("brewery_id").toLong()),
                    if (csvRecord.get("ounces").isEmpty()) 0.0f else csvRecord.get("ounces").toFloat())
                )
            }
            catch (e: Exception){
                println("============================")
                println(csvRecord.get("id"))
            }
        }
    }
}