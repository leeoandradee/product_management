package br.com.fiap.product_management.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import kotlinx.coroutines.launch

class HomeViewModel (
    private val storeLoggedUseCase: StoreLoggedUseCase,
    private val productListUseCase: ProductListUseCase
): ViewModel() {

    var storeLogged = MutableLiveData<RequestState<Store>>()
    var productList = MutableLiveData<RequestState<List<Product>>>()

    fun getStoreLoggedIn() {
        viewModelScope.launch {
            storeLogged.value = storeLoggedUseCase.getStoreLogged()
        }
    }

    fun getProductList() {
        viewModelScope.launch {
            productList.value = productListUseCase.listProducts()
        }
    }

}