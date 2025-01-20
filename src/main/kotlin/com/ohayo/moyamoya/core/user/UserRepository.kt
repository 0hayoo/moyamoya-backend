package com.ohayo.moyamoya.core.user

import com.ohayo.moyamoya.global.CustomException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun existsByPhone(phone: String): Boolean
    fun findByPhone(phone: String): UserEntity?
}

fun UserRepository.findByPhoneSafety(phone: String) =
    findByPhone(phone) ?: throw CustomException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다")