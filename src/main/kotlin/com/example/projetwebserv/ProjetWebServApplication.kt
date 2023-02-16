package com.example.projetwebserv

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "Beers API",
        version = "1.0.0"
    ))

@SpringBootApplication
class ProjetWebServApplication

fun main(args: Array<String>) {
    runApplication<ProjetWebServApplication>(*args)
}
