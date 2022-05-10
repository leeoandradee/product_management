package br.com.fiap.product_management.domain.usecases.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.repository.ProductRepository

class ProductUpdateUserCase (
    private val productRepository: ProductRepository
) {
    suspend fun updateProduct(product: Product): RequestState<Product> =
        productRepository.updateProduct(product)
}