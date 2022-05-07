package br.com.fiap.product_management.domain.repository

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignIn
import br.com.fiap.product_management.domain.entity.store.StoreSignUp

interface StoreRepository {

    suspend fun getStoreLogged(): RequestState<Store>

    suspend fun signIn(storeSignIn: StoreSignIn): RequestState<Store>

    suspend fun signUp(storeSignUp: StoreSignUp): RequestState<Store>
}