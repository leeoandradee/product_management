package br.com.fiap.product_management.presentation.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.domain.usecases.product.*

class ProductDetailsViewModelFactory (
    private val productGetUseCase: ProductGetUseCase,
    private val productDeleteUseCase: ProductDeleteUseCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProductGetUseCase::class.java,
            ProductDeleteUseCase::class.java,
            ).newInstance(productGetUseCase, productDeleteUseCase)
    }
}