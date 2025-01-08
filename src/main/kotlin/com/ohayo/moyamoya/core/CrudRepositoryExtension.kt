package com.ohayo.moyamoya.core

import com.ohayo.moyamoya.global.CustomException
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus

fun <T> CrudRepository<T, Int>.findByIdSafety(id: Int) =
    findByIdOrNull(id) ?: throw CustomException(HttpStatus.NOT_FOUND, "Not found entity")