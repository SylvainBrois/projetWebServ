package com.example.projetwebserv.features.beer.dao.entity
import com.example.projetwebserv.features.beer.model.ViewBeer;
import com.example.projetwebserv.features.brewery.dao.entity.Brewery;
import jakarta.persistence.*

@Entity
data class Beer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int = 0,
    val abv : Float = 0.0f,
    val ibu : Float? = 0.0f,
    val name : String = "",
    val style : String= "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brewery_id")
    val brewery : Brewery? = null,
    val ounces : Float = 0.0f
){
    fun toView() = ViewBeer(id, abv, ibu, name, style, brewery?.id, ounces)
}


   