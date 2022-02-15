package br.com.jhegnerlabs.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtSecretConfig {

    @Autowired
    lateinit var jwtConfig: JwtConfig

    @Bean
    fun secretKeyBean(): SecretKey {
        return Keys.hmacShaKeyFor(jwtConfig.secretKey.toByteArray())
    }

}