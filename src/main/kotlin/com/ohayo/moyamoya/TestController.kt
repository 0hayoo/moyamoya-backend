package com.ohayo.moyamoya

import mu.KLogger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController(
    private val logger: KLogger
) {
    @GetMapping
    fun test(
        @RequestParam param1: String,
    ): String {
        val result = "Hello $param1"
        logger.info(result)
        return result
    }
}