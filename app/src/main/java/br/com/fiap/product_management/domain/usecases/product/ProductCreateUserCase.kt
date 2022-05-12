package br.com.fiap.product_management.domain.usecases.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.repository.ProductRepository

class ProductCreateUserCase (
    private val productRepository: ProductRepository
        ) {
    suspend fun createProduct(product: Product): RequestState<Product> {
        return productRepository.createProduct(product)
    }
}

