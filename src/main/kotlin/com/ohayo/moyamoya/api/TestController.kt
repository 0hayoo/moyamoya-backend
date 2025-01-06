package com.ohayo.moyamoya.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
class ImATeapotException(message: String?) : RuntimeException(message)

@RestController
@RequestMapping("test")
class TestController {
    @GetMapping
    fun test() = "Hello"

    @GetMapping("error")
    fun errorTest() {
        throw ImATeapotException("히히 에러 발싸")
    }
}