package com.ohayo.moyamoya.global

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus,
    message: String? = null,
) : RuntimeException() {
    override val message: String = message ?: status.reasonPhrase
}