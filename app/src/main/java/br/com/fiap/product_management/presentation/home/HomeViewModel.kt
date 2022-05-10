package br.com.fiap.product_management.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import kotlinx.coroutines.launch

class HomeViewModel (
    private val storeLoggedUseCase: StoreLoggedUseCase
): ViewModel() {

    var storeLogged = MutableLiveData<RequestState<Store>>()

    fun getStoreLoggedIn() {
        viewModelScope.launch {
            storeLogged.value = storeLoggedUseCase.getStoreLogged()
        }
    }

}