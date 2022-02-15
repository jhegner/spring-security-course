package br.com.jhegnerlabs.security

import br.com.jhegnerlabs.security.AppUserPermission.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class AppUserRole(
    private val permissions: Set<AppUserPermission>
) {
    ESTUDANTE(emptySet()),
    ADMIN(
        setOf(
            ESTUDANTE_CONSULTAR, ESTUDANTE_INCLUIR, CURSO_CONSULTAR, CURSO_INCLUIR
        )
    ),
    ADMIN_ESTAGIARIO(
        setOf(
            ESTUDANTE_CONSULTAR, CURSO_CONSULTAR,
        )
    );

    fun getGrantedAuthorities(): Set<SimpleGrantedAuthority>{
        val permissions = this.permissions.mapTo(mutableSetOf()) {
            SimpleGrantedAuthority(it.permission)
        }
        permissions.add(SimpleGrantedAuthority("ROLE_" + this.name))
        return permissions
    }
}