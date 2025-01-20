package com.ohayo.moyamoya.core.fcmtoken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FcmTokenRepository: JpaRepository<FcmTokenEntity, Int> {
    fun deleteByFcmToken(fcmToken: String)
    fun findByUserId(userId: Int): List<FcmTokenEntity>
}