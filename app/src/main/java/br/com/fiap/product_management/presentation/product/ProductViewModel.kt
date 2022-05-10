package br.com.fiap.product_management.presentation.product

import androidx.lifecycle.ViewModel
import br.com.fiap.product_management.domain.usecases.product.ProductCreateUserCase
import br.com.fiap.product_management.domain.usecases.product.ProductDeleteUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductUpdateUserCase

class ProductViewModel(
    private val productCreateUserCase: ProductCreateUserCase,
    private val productDeleteUseCase: ProductDeleteUseCase,
    private val productListUseCase: ProductListUseCase,
    private val productUpdateUserCase: ProductUpdateUserCase,
) : ViewModel() {


}