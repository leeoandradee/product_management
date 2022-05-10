package br.com.fiap.product_management.presentation.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignIn
import br.com.fiap.product_management.domain.usecases.store.StoreSignInUseCase
import kotlinx.coroutines.launch

class SigninViewModel(
    private val storeSignInUseCase: StoreSignInUseCase
) : ViewModel() {
    val signinStoreState = MutableLiveData<RequestState<Store>>()

    fun signup(name: String, password: String) {
        viewModelScope.launch {
            signinStoreState.value = storeSignInUseCase.signIn(
                StoreSignIn(
                    name,
                    password,
                )
            )
        }
    }
}