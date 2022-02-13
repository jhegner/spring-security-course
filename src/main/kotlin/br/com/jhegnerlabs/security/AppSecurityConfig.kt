package br.com.jhegnerlabs.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class AppSecurityConfig : WebSecurityConfigurerAdapter() {

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

        val user1 = User.builder()
            .username("user1")
            .password("pwd123")
            .roles("STUDENT") // ROLE_STUDENT
            .build()

        return InMemoryUserDetailsManager(user1)

    }

}