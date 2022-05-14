package br.com.fiap.product_management.domain.usecases.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.repository.ProductRepository
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase

class ProductCreateUserCase (
    private val storeLoggedUseCase: StoreLoggedUseCase,
    private val productRepository: ProductRepository
        ) {
    suspend fun createProduct(product: Product): RequestState<Product> {
        val storeLogged = storeLoggedUseCase.getStoreLogged()
        return when(storeLogged) {
            is RequestState.Success -> {
                product.id = storeLogged.data.id
                productRepository.createProduct(product)
            }

            is RequestState.Loading -> {
                RequestState.Loading
            }

            is RequestState.Error -> {
                RequestState.Error(Exception("Error while creating product"))
            }
        }
    }
}

