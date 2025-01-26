package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.core.user.profile.UserProfileEntity
import com.ohayo.moyamoya.global.CustomException
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.http.HttpStatus
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
    @ColumnDefault("0")
    var maleScore: Int = 0,

    @Column(nullable = false)
    @ColumnDefault("0")
    var femaleScore: Int = 0,

    @Column(nullable = false)
    val score: Int,
) : BaseEntity() {
    val isExpired: Boolean
        get() = createdAt.isBefore(
            LocalDateTime.now()
                .with(LocalTime.NOON) // 12:00 of today
                .plusHours(1) // Add 1 hour
        )

    fun isJoinedUser(userId: Int) =
        userId == male.user.id || userId == female.user.id

    fun assertJoinedUser(userId: Int) {
        if (!isJoinedUser(userId)) {
            throw CustomException(HttpStatus.FORBIDDEN)
        }
    }

    fun addScore(score: Int, user: UserEntity) {
        when (user.gender) {
            Gender.MALE -> this.femaleScore += score
            Gender.FEMALE -> this.maleScore += score
        }
    }
}