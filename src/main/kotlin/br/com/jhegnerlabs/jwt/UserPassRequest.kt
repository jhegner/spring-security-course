package br.com.jhegnerlabs.jwt

data class UserPassRequest(
    val username: String,
    val password: String
) {
    constructor() : this("", "")
}