package br.com.fiap.product_management.domain.usecases.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.repository.ProductRepository

class ProductDeleteUseCase(
private val productRepository: ProductRepository
) {
    suspend fun deleteProduct(product: Product): RequestState<Boolean> =
        productRepository.deleteProduct(product)
}