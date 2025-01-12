package com.ohayo.moyamoya.global

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus,
    override val message: String
) : RuntimeException()