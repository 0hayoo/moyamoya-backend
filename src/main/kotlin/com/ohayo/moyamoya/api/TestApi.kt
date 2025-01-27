package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.core.user.UserRepository
import com.ohayo.moyamoya.core.user.findByPhoneSafety
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.infra.token.JwtClient
import com.ohayo.moyamoya.infra.token.Token
import mu.KLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestApi(
    private val logger: KLogger,
    private val userRepository: UserRepository,
    private val jwtClient: JwtClient,
    @Value("\${dev.secret-key}") private val secretKey: String
) {
    @GetMapping
    fun test(
        @RequestParam param1: String,
    ): String {
        val result = "Hello $param1"
        logger.info(result)
        return result
    }
    
    @GetMapping("token")
    fun getTestToken(
        @RequestParam phone: String,
        @RequestParam key: String
    ): Token {
        if (key != secretKey) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "히히")
        }
        
        return jwtClient.generate(
            userRepository.findByPhoneSafety(phone)
        )
    }
}