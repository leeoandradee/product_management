package br.com.fiap.product_management.domain.entity.product

data class ProductDocument(
    var products: List<Product> = listOf<Product>(),
)