package br.com.fiap.product_management.data.repository

import br.com.fiap.product_management.data.remote.datasource.store.StoreRemoteDataSource
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignIn
import br.com.fiap.product_management.domain.entity.store.StoreSignUp
import br.com.fiap.product_management.domain.repository.StoreRepository

data class StoreRepositoryImpl(
    val storeRemoteDataSource: StoreRemoteDataSource
) : StoreRepository {

    override suspend fun getStoreLogged(): RequestState<Store> {
        return storeRemoteDataSource.getStoreLogged()
    }

    override suspend fun signIn(storeSignIn: StoreSignIn): RequestState<Store> {
        return storeRemoteDataSource.signIn(storeSignIn)
    }

    override suspend fun signUp(storeSignUp: StoreSignUp): RequestState<Store> {
        return storeRemoteDataSource.signUp(storeSignUp)
    }
}