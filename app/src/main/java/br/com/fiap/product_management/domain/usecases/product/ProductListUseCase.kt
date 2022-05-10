package br.com.fiap.product_management.domain.usecases.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.repository.ProductRepository

class ProductListUseCase(
    private val productRepository: ProductRepository
) {
    suspend fun listProducts(): RequestState<MutableList<Product>> =
        productRepository.getProducts()
}