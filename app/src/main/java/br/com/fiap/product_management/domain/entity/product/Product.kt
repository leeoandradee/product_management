package br.com.fiap.product_management.domain.entity.product

data class Product(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var amount: Int = 0,
)