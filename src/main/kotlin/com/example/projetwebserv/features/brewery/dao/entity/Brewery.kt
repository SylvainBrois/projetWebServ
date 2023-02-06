package com.example.projetwebserv.features.brewery.dao.entity
import com.example.projetwebserv.features.beer.dao.entity.Beer
import com.example.projetwebserv.features.brewery.model.ViewBrewery
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Brewery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name : String = "",
    val city : String = "",
    val state : String = "",
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "brewery")
    private val _beers: MutableList<Beer> = mutableListOf<Beer>()
) {
    val beers get() = _beers.toList()

    fun toView() = ViewBrewery(id,name,city, state)
}