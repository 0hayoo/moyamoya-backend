package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.global.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController {
    @GetMapping
    fun test() = "Hello"
    
    @GetMapping("error")
    fun errorTest() {
        throw CustomException(HttpStatus.I_AM_A_TEAPOT, "히히 에러 발싸")
    }
}