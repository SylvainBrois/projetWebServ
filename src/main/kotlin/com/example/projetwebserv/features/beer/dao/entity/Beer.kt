package com.example.projetwebserv.features.beer.dao.entity
import com.example.projetwebserv.features.brewery.dao.entity.Brewery;
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonUnwrapped
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
    @JsonIgnore
    @JoinColumn(name = "brewery_id")
    val _brewery : Brewery? = null,
    val ounces : Float = 0.0f
){
    val brewery get() = _brewery?.id
}


   