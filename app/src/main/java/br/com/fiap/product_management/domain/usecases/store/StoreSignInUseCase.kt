package br.com.fiap.product_management.domain.usecases.store

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignIn
import br.com.fiap.product_management.domain.repository.StoreRepository
import java.lang.Exception

class StoreSignInUseCase(
    private val storeRepository: StoreRepository
) {

    suspend fun signIn(storeSignIn: StoreSignIn): RequestState<Store> {

        if (storeSignIn.email.isBlank()) {
            return RequestState.Error(Exception("Email can't be empty"))
        }

        if (storeSignIn.password.isBlank()) {
            return RequestState.Error(Exception("Password can't be empty"))
        }

        return storeRepository.signIn(storeSignIn)
    }
}