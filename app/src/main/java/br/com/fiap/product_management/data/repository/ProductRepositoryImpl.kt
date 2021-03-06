package br.com.fiap.product_management.data.repository

import br.com.fiap.product_management.data.remote.datasource.product.ProductRemoteDataSource
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.repository.ProductRepository

data class ProductRepositoryImpl (
        val productRemoteDataSource: ProductRemoteDataSource
): ProductRepository {

        override suspend fun getProduct(id: String): RequestState<Product> {
                return productRemoteDataSource.getProduct(id)
        }

        override suspend fun getProducts(): RequestState<List<Product>> {
                return productRemoteDataSource.getProducts()
        }

        override suspend fun createProduct(product: Product): RequestState<Product> {
                return productRemoteDataSource.createProduct(product)
        }

        override suspend fun updateProduct(product: Product): RequestState<Product> {
                return productRemoteDataSource.updateProduct(product)
        }

        override suspend fun deleteProduct(product: Product): RequestState<Boolean> {
                return productRemoteDataSource.deleteProduct(product)
        }


}