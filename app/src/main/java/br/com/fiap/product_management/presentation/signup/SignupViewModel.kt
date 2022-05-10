package br.com.fiap.product_management.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignUp
import br.com.fiap.product_management.domain.usecases.store.StoreSignUpUseCase
import kotlinx.coroutines.launch

class SignupViewModel (
    private val storeSignUpUseCase: StoreSignUpUseCase
) : ViewModel() {
    val newStoreState = MutableLiveData<RequestState<Store>>()

    fun signup(name: String, cnpj: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            newStoreState.value = storeSignUpUseCase.signUp(
                StoreSignUp(
                    name,
                    cnpj,
                    email,
                    password,
                    confirmPassword
                )
            )
        }
    }
}