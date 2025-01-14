package com.ohayo.moyamoya.global.config

import com.ohayo.moyamoya.global.ErrorResponseSender
import com.ohayo.moyamoya.global.HttpExceptionFilter
import com.ohayo.moyamoya.global.jwt.JwtAuthenticationFilter
import com.ohayo.moyamoya.global.jwt.JwtExceptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val httpExceptionFilter: HttpExceptionFilter,
    private val sender: ErrorResponseSender
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain = http
        .cors { corsConfigurationSource() }
        .csrf { it.disable() }
        .formLogin { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .authorizeHttpRequests {
            it.requestMatchers(
                // Features
                "/users/send-code",
                "/users/verify-code",
                "/users/sign-up",
                "/users/refresh",
                "/users/available-profiles",
                
                "/schools",

                // ETC
                "test/**",
                // Swagger
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**",
            ).permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter::class.java)
        .addFilterBefore(httpExceptionFilter, JwtExceptionFilter::class.java)
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

    @Bean
    fun securityCustomizer() = WebSecurityCustomizer { web: WebSecurity ->
        web.ignoring()
            .requestMatchers("swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**")
    }
}