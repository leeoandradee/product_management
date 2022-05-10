package br.com.fiap.product_management.presentation.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.usecases.store.StoreLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel(
    private val storeLoggedUseCase: StoreLoggedUseCase
) : ViewModel() {

    val userLoggedState = MutableLiveData<RequestState<Store>>()

    fun getUserLogged () {
        viewModelScope.launch {
            userLoggedState.value = storeLoggedUseCase.getStoreLogged()
        }
    }
}