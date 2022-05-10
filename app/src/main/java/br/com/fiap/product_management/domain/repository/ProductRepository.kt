package br.com.fiap.product_management.domain.repository

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.entity.store.Store

interface ProductRepository {

    suspend fun getProducts(): RequestState<MutableList<Product>>

    suspend fun createProduct(product: Product): RequestState<Product>

    suspend fun updateProduct(product: Product): RequestState<Product>

    suspend fun deleteProduct(product: Product): RequestState<Boolean>
}