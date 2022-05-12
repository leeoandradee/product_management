package br.com.fiap.product_management.data.remote.datasource.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product

interface ProductRemoteDataSource {

    suspend fun getProducts(): RequestState<List<Product>>

    suspend fun createProduct(product: Product): RequestState<Product>

    suspend fun updateProduct(product: Product): RequestState<Product>

    suspend fun deleteProduct(product: Product): RequestState<Boolean>
}