package br.com.fiap.product_management.presentation.product_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.domain.usecases.product.*

class ProductUpdateViewModelFactory (
    private val productGetUseCase: ProductGetUseCase,
    private val productUpdateUserCase: ProductUpdateUserCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProductGetUseCase::class.java,
            ProductUpdateUserCase::class.java,
            ).newInstance(productGetUseCase, productUpdateUserCase)
    }
}