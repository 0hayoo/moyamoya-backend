package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.core.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserProfileRepository: JpaRepository<UserProfileEntity, Int> {
    fun findByUser(user: UserEntity): UserProfileEntity?
}