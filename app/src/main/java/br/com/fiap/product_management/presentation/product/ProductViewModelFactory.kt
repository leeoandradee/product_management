package br.com.fiap.product_management.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.product_management.domain.usecases.product.ProductCreateUserCase
import br.com.fiap.product_management.domain.usecases.product.ProductDeleteUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductUpdateUserCase

class ProductViewModelFactory (
    private val productCreateUserCase: ProductCreateUserCase,
    private val productDeleteUseCase: ProductDeleteUseCase,
    private val productListUseCase: ProductListUseCase,
    private val productUpdateUserCase: ProductUpdateUserCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProductCreateUserCase::class.java,
            ProductDeleteUseCase::class.java,
            ProductListUseCase::class.java,
            ProductUpdateUserCase::class.java,

        ).newInstance(productCreateUserCase, productDeleteUseCase, productListUseCase, productUpdateUserCase)
    }
}