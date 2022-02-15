package br.com.jhegnerlabs.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenVerifier(

    private val jwtConfig: JwtConfig,
    private val secretKey: SecretKey

) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader(jwtConfig.getAuthorizationHeader())

        if (Objects.isNull(authHeader) || !authHeader.startsWith("${jwtConfig.tokenPrefix} ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.replace("${jwtConfig.tokenPrefix} ", "")

        try {

            val parsedToken = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parse(token)

            val body = parsedToken.body as Claims

            val nomeUsuario = body.subject

            val authoritiesMap = body["authorities"] as List<Map<String, String>>

            val simpleGrantedAuthority = authoritiesMap
                .mapTo(mutableSetOf()) {
                    SimpleGrantedAuthority(it["authority"])
                }

            val authentication = UsernamePasswordAuthenticationToken(
                nomeUsuario,
                null,
                simpleGrantedAuthority
            )
            SecurityContextHolder.getContext().authentication = authentication

        } catch (ex: JwtException) {
            throw IllegalStateException("Token invalido $token")
        }

        filterChain.doFilter(request, response)
    }
}