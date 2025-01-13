package com.ohayo.moyamoya.common

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

fun Any.toJson(): String = try {
    val objectMapper = jacksonObjectMapper().apply { 
        registerModule(JavaTimeModule())
    }
    objectMapper.writeValueAsString(this)
} catch (e: Exception) {
    throw RuntimeException(e)
}

inline fun <reified T> String.fromJson(): T = try {
    jacksonObjectMapper().readValue<T>(this)
} catch (e: Exception) {
    throw RuntimeException(e)
}