package br.com.fiap.product_management.presentation.product_update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.usecases.product.ProductGetUseCase
import br.com.fiap.product_management.domain.usecases.product.ProductUpdateUserCase
import kotlinx.coroutines.launch

class ProductUpdateViewModel  (
    private val productGetUseCase: ProductGetUseCase,
    private val productUpdateUseCase: ProductUpdateUserCase,
) : ViewModel() {

    val productSelected = MutableLiveData<RequestState<Product>>()
    val productUpdated = MutableLiveData<RequestState<Product>>()

    fun getProduct(id: String) {
        viewModelScope.launch {
            productSelected.value = productGetUseCase.getProduct(id)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productUpdated.value = productUpdateUseCase.updateProduct(product)
        }
    }


}