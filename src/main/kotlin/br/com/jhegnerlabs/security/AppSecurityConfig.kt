package br.com.jhegnerlabs.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class AppSecurityConfig(

    @Autowired
    private val passwordEncoder: PasswordEncoder

) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
    }

    @Override
    @Bean
    override fun userDetailsService(): UserDetailsService {

        val userDetails1 = User.builder()
            .username("paulo")
            .password(passwordEncoder.encode("pwd123"))
            .roles("STUDENT") // ROLE_STUDENT
            .build()

        val userDetails2 = User.builder()
            .username("maria")
            .password(passwordEncoder.encode("q1w2e3r4"))
            .roles("ADMIN") // ROLE_ADMIN
            .build()

        return InMemoryUserDetailsManager(userDetails1, userDetails2)

    }

}