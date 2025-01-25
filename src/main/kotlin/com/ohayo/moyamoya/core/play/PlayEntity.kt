package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.user.profile.UserProfileEntity
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "tbl_play")
class PlayEntity(
    @JoinColumn(nullable = false)
    @ManyToOne
    val male: UserProfileEntity,

    @JoinColumn(nullable = false)
    @ManyToOne
    val female: UserProfileEntity,

    @Column(nullable = false)
    val score: Int,
) : BaseEntity() {
    val isExpired: Boolean
        get() = createdAt.isBefore(
            LocalDateTime.now()
                .with(LocalTime.NOON) // 12:00 of today
                .plusHours(1) // Add 1 hour
        )
}