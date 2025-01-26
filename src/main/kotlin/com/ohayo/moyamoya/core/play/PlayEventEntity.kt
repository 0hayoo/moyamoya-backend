package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.question.SubjectEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_play_event")
class PlayEventEntity(
    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    val eventTime: LocalDateTime,

    @JoinColumn(name = "play_id", nullable = false)
    @ManyToOne
    val play: PlayEntity,

    @JoinColumn(name = "subject_id", nullable = false)
    @OneToOne(cascade = [CascadeType.PERSIST])
    val subject: SubjectEntity,
) : BaseEntity() {
    fun assertJoinedUser(userId: Int) {
        play.assertJoinedUser(userId)
    }
}