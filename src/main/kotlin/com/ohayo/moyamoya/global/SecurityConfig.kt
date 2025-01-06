package com.ohayo.moyamoya.global

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val httpExceptionFilter: HttpExceptionFilter,
    private val sender: ErrorResponseSender
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain = http
        .cors { corsConfigurationSource() }
        .csrf { it.disable() }
        .formLogin { it.disable() }
        .sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.requestMatchers(
                "auth/refresh",
                "test/**"
            ).permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(httpExceptionFilter, UsernamePasswordAuthenticationFilter::class.java)
        .exceptionHandling {
            it.authenticationEntryPoint { _, response, _ -> sender.send(response, HttpStatus.UNAUTHORIZED) }
            it.accessDeniedHandler { _, response, _ -> sender.send(response, HttpStatus.FORBIDDEN) }
        }
        .build()

    @Bean
    fun corsConfigurationSource() = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            addAllowedOriginPattern(CorsConfiguration.ALL)  // Allows any origin
            addAllowedHeader(CorsConfiguration.ALL)         // Allows any header
            addAllowedMethod(CorsConfiguration.ALL)         // Allows any HTTP method
            allowCredentials = true                         // Allows cookies and credentials
        })
    }
}