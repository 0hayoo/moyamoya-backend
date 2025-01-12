package com.ohayo.moyamoya.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PhoneCodeRepository: JpaRepository<PhoneCodeEntity, Int> {
    @Query("SELECT m FROM PhoneCodeEntity m WHERE m.isActive = true AND m.phone = :phone AND m.code = :code")
    fun findByIsActive(phone: String, code: String): List<PhoneCodeEntity>
}