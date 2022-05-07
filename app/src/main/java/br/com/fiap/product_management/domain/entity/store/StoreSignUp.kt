package br.com.fiap.product_management.domain.entity.store

data class StoreSignUp (
    val name: String = "",
    val cnpj: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)