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
    var isActive: Boolean = true
) {

    fun disable() {
        this.isActive = false
    }
}