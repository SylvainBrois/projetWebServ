package com.example.projetwebserv.features.components.populate.parsers

import com.example.projetwebserv.features.brewery.dao.entity.Brewery
import com.example.projetwebserv.features.brewery.dao.repository.BreweryRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

class BreweryParser(var resourceLoader: ResourceLoader,var breweyDao: BreweryRepository) {
    fun parse(filePath : String) {
        val resource: Resource = resourceLoader.getResource(filePath)
        val inputStream: InputStream = resource.inputStream
        val reader: Reader = InputStreamReader(inputStream)
        var csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
        val csvParser = CSVParser(reader,csvFormat)

        for (csvRecord in csvParser) {
            try{
                breweyDao.save(Brewery(0, csvRecord.get("name").toString(),csvRecord.get("city").toString(), csvRecord.get("state")))
            }
            catch (exception: NumberFormatException){
                exception.printStackTrace()
            }
        }
    }


}