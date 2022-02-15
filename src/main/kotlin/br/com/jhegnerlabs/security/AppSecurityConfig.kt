package br.com.jhegnerlabs.security

import br.com.jhegnerlabs.security.AppUserPermission.CURSO_INCLUIR
import br.com.jhegnerlabs.security.AppUserRole.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AppSecurityConfig(

    @Autowired
    private val passwordEncoder: PasswordEncoder

) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
//            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ESTUDANTE.name)
//            .antMatchers(DELETE,"/management/api/**").hasAuthority(CURSO_INCLUIR.permission)
//            .antMatchers(POST,"/management/api/**").hasAuthority(CURSO_INCLUIR.permission)
//            .antMatchers(PUT,"/management/api/**").hasAuthority(CURSO_INCLUIR.permission)
//            .antMatchers(GET,"/management/api/**").hasAnyRole(ADMIN.name, ADMIN_ESTAGIARIO.name)
            .anyRequest()
            .authenticated()
            .and()
//            .httpBasic()
            .formLogin()
    }

    @Override
    @Bean
    override fun userDetailsService(): UserDetailsService {

        val userDetails1 = User.builder()
            .username("paulo")
            .password(passwordEncoder.encode("123"))
//            .roles(ESTUDANTE.name) // ROLE_STUDENT
            .authorities(ESTUDANTE.getGrantedAuthorities())
            .build()

        val userDetails2 = User.builder()
            .username("maria")
            .password(passwordEncoder.encode("123"))
//            .roles(ADMIN.name) // ROLE_ADMIN
            .authorities(ADMIN.getGrantedAuthorities())
            .build()

        val userDetails3 = User.builder()
            .username("kim")
            .password(passwordEncoder.encode("123"))
//            .roles(ADMIN_ESTAGIARIO.name) // ROLE_ADMIN_ESTAGIARIO
            .authorities(ADMIN_ESTAGIARIO.getGrantedAuthorities())
            .build()

        return InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails3)

    }

}