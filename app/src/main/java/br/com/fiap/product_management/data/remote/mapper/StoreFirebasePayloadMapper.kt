package br.com.fiap.product_management.data.remote.mapper

import br.com.fiap.product_management.data.models.StoreFirebasePayload
import br.com.fiap.product_management.domain.entity.store.Store
import br.com.fiap.product_management.domain.entity.store.StoreSignUp

object StoreFirebasePayloadMapper {

    fun mapToNewStore(storeFirebasePayload: StoreFirebasePayload) = StoreSignUp(
        name = storeFirebasePayload.name ?: "",
        email = storeFirebasePayload.email ?: "",
        cnpj = storeFirebasePayload.cnpj ?: "",
        password = storeFirebasePayload.password ?: ""
    )

    fun mapTostoreFirebasePayload(storeSignUp: StoreSignUp) = StoreFirebasePayload(
        name = storeSignUp.name,
        email = storeSignUp.email,
        cnpj = storeSignUp.cnpj,
        password = storeSignUp.password
    )

    fun mapToStore(storeFirebasePayload: StoreFirebasePayload) = Store(
        name = storeFirebasePayload.name ?: ""
    )
}