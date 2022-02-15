package br.com.jhegnerlabs.jwt

import com.google.common.net.HttpHeaders
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
data class JwtConfig(
    var secretKey: String,
    var tokenPrefix: String,
    var tokenExpirationAfterDays: Long
) {
    constructor() : this("", "", 0)

    fun getAuthorizationHeader(): String {
        return HttpHeaders.AUTHORIZATION
    }
}