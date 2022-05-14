package br.com.fiap.product_management.presentation.product_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.usecases.product.*
import kotlinx.coroutines.launch

class ProductDetailsViewModel (
    private val productGetUseCase: ProductGetUseCase,
    private val productDeleteUseCase: ProductDeleteUseCase,
) : ViewModel() {

    val productSelected = MutableLiveData<RequestState<Product>>()
    val productDeleted = MutableLiveData<RequestState<Boolean>>()

    fun getProduct(id: String) {
        viewModelScope.launch {
            productSelected.value = productGetUseCase.getProduct(id)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDeleted.value = productDeleteUseCase.deleteProduct(product)
        }
    }


}