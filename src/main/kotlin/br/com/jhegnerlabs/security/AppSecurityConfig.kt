package br.com.jhegnerlabs.security

import br.com.jhegnerlabs.security.AppUserPermission.CURSO_INCLUIR
import br.com.jhegnerlabs.security.AppUserRole.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.*
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
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ESTUDANTE.name)
            .antMatchers(DELETE,"/management/api/**").hasAuthority(CURSO_INCLUIR.name)
            .antMatchers(POST,"/management/api/**").hasAuthority(CURSO_INCLUIR.name)
            .antMatchers(PUT,"/management/api/**").hasAuthority(CURSO_INCLUIR.name)
            .antMatchers(GET,"/management/api/**").hasAnyRole(ADMIN.name, ADMIN_ESTAGIARIO.name)
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
            .roles(ESTUDANTE.name)
            .build()

        val userDetails2 = User.builder()
            .username("maria")
            .password(passwordEncoder.encode("q1w2e3r4"))
            .roles(ADMIN.name)
            .build()

        val userDetails3 = User.builder()
            .username("kim")
            .password(passwordEncoder.encode("123"))
            .roles(ADMIN_ESTAGIARIO.name)
            .build()

        return InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails3)

    }

}