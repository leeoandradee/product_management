package br.com.fiap.product_management.data.remote.datasource.store.impl

import br.com.fiap.product_management.data.remote.datasource.store.StoreRemoteDataSource
import br.com.fiap.product_management.data.remote.mapper.StoreFirebasePayloadMapper
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignIn
import br.com.fiap.product_management.domain.entity.store.StoreSignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import java.lang.Exception

@ExperimentalCoroutinesApi
class StoreRemoteDataSourceImpl (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
        ): StoreRemoteDataSource {

    override suspend fun getStoreLogged(): RequestState<Store> {
        firebaseAuth.currentUser?.reload()

        val firebaseStore = firebaseAuth.currentUser

        return if(firebaseStore == null) {
            RequestState.Error(Exception("Store not found"))
        } else {
            val store = firebaseFirestore.collection("stores")
                .document(firebaseStore.uid).get().await().toObject(Store::class.java)

            if(store == null) {
                RequestState.Error(Exception("Store not found"))
            } else {
                store.id = firebaseStore.uid
                RequestState.Success(store)
            }
        }
    }

    override suspend fun signIn(storeSignIn: StoreSignIn): RequestState<Store> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(storeSignIn.email, storeSignIn.password).await()

            val firebaseStore = firebaseAuth.currentUser

            if(firebaseStore == null) {
                RequestState.Error(Exception("Email or password is invalid"))
            } else {
                RequestState.Success(Store(firebaseStore.displayName ?: ""))
            }

        } catch (e: Exception) {
            RequestState.Error(Exception(e))
        }
    }

    override suspend fun signUp(storeSignUp: StoreSignUp): RequestState<Store> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(storeSignUp.email, storeSignUp.password).await()
            val storeId = firebaseAuth.currentUser?.uid

            if (storeId == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {

                val storeSignUpFirebasePayload =
                    StoreFirebasePayloadMapper.mapTostoreFirebasePayload(storeSignUp)
                firebaseFirestore
                    .collection("stores")
                    .document(storeId)
                    .set(storeSignUpFirebasePayload)
                    .await()
                RequestState.Success(StoreFirebasePayloadMapper.mapToStore(storeSignUpFirebasePayload))
            }
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }

}