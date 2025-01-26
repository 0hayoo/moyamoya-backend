package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "tbl_play_event_review",
    uniqueConstraints = [
        UniqueConstraint(
            name = "UniqueUserAndPlayEvent",
            columnNames = ["user_id", "play_event_id"]
        )
    ]
)
class PlayEventReviewEntity(
    @Column(nullable = false)
    val star: Int,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: UserEntity,

    @JoinColumn(name = "play_event_id", nullable = false)
    @ManyToOne
    val playEvent: PlayEventEntity
) : BaseEntity()