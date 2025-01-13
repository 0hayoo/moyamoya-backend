package com.ohayo.moyamoya.core

import jakarta.persistence.*

@Entity
@Table(name = "tbl_phone_code")
class PhoneCodeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    
    @Column(nullable = false)
    val phone: String,
    
    @Column(nullable = false)
    val code: String,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: Status = Status.UNUSED
) {
    enum class Status(val nextStep: Status?) {
        SIGNED_UP(null),
        AUTHORIZED(SIGNED_UP), 
        UNUSED(AUTHORIZED),
    }
    
    fun updateStatus() {
        if (status.nextStep != null) {
            status = status.nextStep!!
        }
    }
}