package br.com.fiap.product_management.domain.usecases.store

import br.com.fiap.product_management.domain.entity.RequestState
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.repository.StoreRepository

class StoreLoggedUseCase(
    private val storeRepository: StoreRepository
) {
    suspend fun getStoreLogged(): RequestState<Store> =
        storeRepository.getStoreLogged()
}