package br.com.fiap.product_management.data.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class StoreFirebasePayload(
    val name: String? = null,
    val email: String? = null,
    val cnpj: String? = null,
    @get:Exclude val password: String? = null
)