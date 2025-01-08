package com.ohayo.moyamoya.core

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tbl_school")
class SchoolEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String,

    @Enumerated(value = EnumType.STRING)
    val type: SchoolType?,

    @Column(nullable = false)
    val cityName: String,

    val postalCode: String?,
    val address: String?,
    val addressDetail: String?,

    @Column(nullable = false)
    val phone: String,
    
    val website: String?,

    @Column(nullable = false)
    val createdAt: LocalDate,

    @Column(nullable = false)
    val anniversary: LocalDate,

    @Column(nullable = false)
    val schoolCode: String,

    @Column(nullable = false)
    val officeCode: String,
)