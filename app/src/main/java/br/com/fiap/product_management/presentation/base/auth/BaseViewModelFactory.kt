package br.com.fiap.product_management.presentation.base.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.domain.usecases.product.StoreLoggedUseCase

class BaseViewModelFactory(
    private val storeLoggedUseCase: StoreLoggedUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(StoreLoggedUseCase::class.java).newInstance(storeLoggedUseCase)
    }
}