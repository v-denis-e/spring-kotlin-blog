package kz.vdenise.springkotlinblog.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Profile("default")
class SecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.headers()?.frameOptions()?.sameOrigin()
            ?.and()
            ?.authorizeRequests()
            ?.antMatchers("/", "/index", "/index.html")?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.oauth2Login()
            ?.defaultSuccessUrl("/profile")
    }
}