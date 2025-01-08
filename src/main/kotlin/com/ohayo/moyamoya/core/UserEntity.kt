package com.ohayo.moyamoya.core

import jakarta.persistence.*

@Entity
@Table(name = "tbl_user")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    phone: String,
    schoolName: String,
    schoolGrade: Int,
    name: String,
    password: String,
    profileImageUrl: String,
)