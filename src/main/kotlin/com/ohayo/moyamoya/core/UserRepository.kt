package com.ohayo.moyamoya.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Int> {
    fun existsByTel(tel: String): Boolean
}