package com.ohayo.moyamoya.core

import com.ohayo.moyamoya.global.CustomException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun existsByTel(tel: String): Boolean

    fun findByTel(tel: String): UserEntity?
}

fun UserRepository.findByTelSafety(tel: String) =
    findByTel(tel) ?: throw CustomException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다")