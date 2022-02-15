package br.com.jhegnerlabs.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.LocalDate
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class JwtAuthFilter(

    private val authManager: AuthenticationManager,
    private val jwtConfig: JwtConfig,
    private val secretKey: SecretKey

) : UsernamePasswordAuthenticationFilter() {

    @Throws(IOException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val authRequest = ObjectMapper().readValue(request?.inputStream, UserPassRequest::class.java)

        val authentication = getAuthentication(authRequest)

        return authManager.authenticate(authentication)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        response!!.addHeader(jwtConfig.getAuthorizationHeader(), "${jwtConfig.tokenPrefix} ${getToken(authResult)}")
        response.status = HttpServletResponse.SC_ACCEPTED

    }

    private fun getToken(authResult: Authentication?): String? = Jwts.builder()
        .setSubject(authResult?.name)
        .claim("authorities", authResult?.authorities)
        .setIssuedAt(Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.tokenExpirationAfterDays)))
        .signWith(secretKey)
        .compact()

    private fun getAuthentication(request: UserPassRequest) =
        UsernamePasswordAuthenticationToken(request.username, request.password)
}