package com.ohayo.moyamoya.core

import jakarta.persistence.*

@Entity
@Table(name = "tbl_fcm_token")
class FcmTokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    
    @Column(nullable = false)
    val fcmToken: String,
    
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user: UserEntity,
)