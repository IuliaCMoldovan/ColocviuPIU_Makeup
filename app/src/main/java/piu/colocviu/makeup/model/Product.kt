package piu.colocviu.makeup.model

import java.io.Serializable

data class Product(
    var nume: String,
    var brand: String,
    var descriere: String,
    var pret: String,
    var categorie: String,
    var imagine: Int

) : Serializable