package br.com.jhegnerlabs.security

import br.com.jhegnerlabs.security.AppUserPermission.*

enum class AppUserRole(
    val permissions: Set<AppUserPermission>
) {
    ESTUDANTE(emptySet()),
    ADMIN(
        setOf(
            ESTUDANTE_CONSULTAR,
            ESTUDANTE_INCLUIR,
            CURSO_CONSULTAR,
            CURSO_INCLUIR
        )
    ),
    ADMIN_ESTAGIARIO(
        setOf(
            ESTUDANTE_CONSULTAR,
            CURSO_CONSULTAR,
        )
    )
}