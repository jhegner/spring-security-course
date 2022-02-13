package br.com.jhegnerlabs.security

enum class AppUserPermission(
    val permission: String
) {

    ESTUDANTE_CONSULTAR("estudante:consulta"),
    ESTUDANTE_INCLUIR("estudante:incluir"),
    CURSO_CONSULTAR("curso:consulta"),
    CURSO_INCLUIR("curso:incluir")

}