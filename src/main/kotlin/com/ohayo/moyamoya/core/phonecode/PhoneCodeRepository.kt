package com.ohayo.moyamoya.core.phonecode

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneCodeRepository: JpaRepository<PhoneCodeEntity, Int> {
    fun findByStatusAndPhoneAndCode(status: PhoneCodeEntity.Status, phone: String, code: String): List<PhoneCodeEntity>
}