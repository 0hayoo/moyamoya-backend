package com.ohayo.moyamoya.core.phonecode

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_phone_code")
class PhoneCodeEntity(
    @Column(nullable = false)
    val phone: String,
    
    @Column(nullable = false)
    val code: String,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: Status = Status.UNUSED
): BaseEntity() {
    enum class Status(val nextStep: Status?) {
        SIGNED_UP(nextStep = null), // 회원가입이 된 상태
        AUTHORIZED(nextStep = SIGNED_UP),  // 인증 코드로 인증 된 상태
        UNUSED(nextStep = AUTHORIZED), // 초기 상태
    }
    
    fun updateStatus() {
        if (status.nextStep != null) {
            status = status.nextStep!!
        }
    }
}