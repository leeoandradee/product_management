package br.com.fiap.product_management.data.remote.datasource.product.impl

import br.com.fiap.product_management.data.remote.datasource.product.ProductRemoteDataSource
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.entity.product.ProductDocument
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class ProductRemoteDataSourceImpl (
    private val firebaseFirestore: FirebaseFirestore
        ): ProductRemoteDataSource {

    override suspend fun getProducts(): RequestState<List<Product>> {
        return try {

            val productList = firebaseFirestore.collection("products")
                .get()
                .await()
                .toObjects(Product::class.java)

            RequestState.Success(productList)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun createProduct(product: Product): RequestState<Product> {
        return try {
            firebaseFirestore.collection("products")
                .add(product)
                .await()
            RequestState.Success(product)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun updateProduct(product: Product): RequestState<Product> {
        return try {
            firebaseFirestore.collection("products")
                .document(product.userId)
                .set(product)
                .await()
            RequestState.Success(product)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun deleteProduct(product: Product): RequestState<Boolean> {
        return try {
            firebaseFirestore.collection("products")
                .document(product.userId)
                .delete()
                .await()
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }


}