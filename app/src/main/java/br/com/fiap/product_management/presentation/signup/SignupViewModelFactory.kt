package br.com.fiap.product_management.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.domain.usecases.product.StoreSignUpUseCase

class SignupViewModelFactory (
    private val storeSignUpUseCase: StoreSignUpUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(StoreSignUpUseCase::class.java)
            .newInstance(storeSignUpUseCase)
    }
}