package br.com.fiap.product_management.presentation.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.usecases.product.ProductCreateUserCase
import br.com.fiap.product_management.domain.usecases.product.ProductDeleteUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductUpdateUserCase
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productCreateUserCase: ProductCreateUserCase,
    private val productDeleteUseCase: ProductDeleteUseCase,
    private val productListUseCase: ProductListUseCase,
    private val productUpdateUserCase: ProductUpdateUserCase,
) : ViewModel() {

    val productCreateStoreState = MutableLiveData<RequestState<Product>>()

    fun createProduct(name: String, price: Double, amount: Int) {
        viewModelScope.launch {
            productCreateStoreState.value = productCreateUserCase.createProduct(
                Product(
                    "",
                    name,
                    price,
                    amount
                )
            )
        }
    }


}