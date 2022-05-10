package br.com.fiap.product_management.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.domain.usecases.store.StoreSignInUseCase

class SigninViewModelFactory (
    private val storeSignInUseCase: StoreSignInUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(StoreSignInUseCase::class.java)
            .newInstance(storeSignInUseCase)
    }
}