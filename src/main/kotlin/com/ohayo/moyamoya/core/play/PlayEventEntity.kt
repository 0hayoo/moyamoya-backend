package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.question.SubjectEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
    @ManyToOne
    val subject: SubjectEntity,
) : BaseEntity() {
    fun assertJoinedUser(userId: Int) {
        play.assertJoinedUser(userId)
    }
}