package br.com.jhegnerlabs.security

import br.com.jhegnerlabs.jwt.JwtAuthFilter
import br.com.jhegnerlabs.jwt.JwtConfig
import br.com.jhegnerlabs.jwt.JwtTokenVerifier
import br.com.jhegnerlabs.security.AppUserRole.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import javax.crypto.SecretKey

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AppSecurityConfig(

    private val passwordEncoder: PasswordEncoder,
    private val jwtConfig: JwtConfig,
    private val secretKey: SecretKey

) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilter(JwtAuthFilter(authenticationManager(), jwtConfig, secretKey))
            .addFilterAfter(JwtTokenVerifier(jwtConfig, secretKey), JwtAuthFilter::class.java)
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ESTUDANTE.name).anyRequest()
            .authenticated()
    }

    @Override
    @Bean
    override fun userDetailsService(): UserDetailsService {

        val userDetails1 = User.builder()
            .username("paulo")
            .password(passwordEncoder.encode("123"))
            .authorities(ESTUDANTE.getGrantedAuthorities())
            .build()

        val userDetails2 = User.builder()
            .username("maria")
            .password(passwordEncoder.encode("123"))
            .authorities(ADMIN.getGrantedAuthorities())
            .build()

        val userDetails3 = User.builder()
            .username("kim")
            .password(passwordEncoder.encode("123"))
            .authorities(ADMIN_ESTAGIARIO.getGrantedAuthorities())
            .build()

        return InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails3)

    }

}