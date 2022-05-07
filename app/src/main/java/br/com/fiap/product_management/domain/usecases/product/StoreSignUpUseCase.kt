package br.com.fiap.product_management.domain.usecases.product

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignUp
import br.com.fiap.product_management.domain.repository.StoreRepository
import java.lang.Exception

class StoreSignUpUseCase(
    private val storeRepository: StoreRepository
) {

    suspend fun signUp(storeSignUp: StoreSignUp): RequestState<Store> {
        if (storeSignUp.email.isBlank()) {
            RequestState.Error(Exception("Email can't be empty"))
        }

        if (storeSignUp.cnpj.isBlank()) {
            RequestState.Error(Exception("CNPJ can't be empty"))
        }

        if (storeSignUp.password.isBlank()) {
            RequestState.Error(Exception("Password can't be empty"))
        }

        if (storeSignUp.confirmPassword.isBlank()) {
            RequestState.Error(Exception("Confirm password can't be empty"))
        }

        if (storeSignUp.password == storeSignUp.password) {
            RequestState.Error(Exception("Passwords must be equals"))
        }

        return storeRepository.signUp(storeSignUp)
    }

}