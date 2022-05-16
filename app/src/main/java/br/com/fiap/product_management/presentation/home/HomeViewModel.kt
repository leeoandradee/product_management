package br.com.fiap.product_management.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.product.Product
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.usecases.product.ProductListUseCase
import br.com.fiap.product_management.domain.usecases.store.StoreLogoutUseCase
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import kotlinx.coroutines.launch

class HomeViewModel (
    private val storeLoggedUseCase: StoreLoggedUseCase,
    private val productListUseCase: ProductListUseCase,
    private val logoutUseCase: StoreLogoutUseCase
): ViewModel() {

    var storeLogged = MutableLiveData<RequestState<Store>>()
    var productList = MutableLiveData<RequestState<List<Product>>>()
    var storeLogout = MutableLiveData<RequestState<Boolean>>()

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

    fun logout() {
        viewModelScope.launch {
            storeLogout.value = logoutUseCase.logout()
        }
    }

}