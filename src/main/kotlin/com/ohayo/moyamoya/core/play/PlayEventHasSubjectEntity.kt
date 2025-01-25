package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.question.SubjectEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_play_event_has_subject")
class PlayEventHasSubjectEntity(
    @JoinColumn(name = "play_event_id", nullable = false)
    @ManyToOne
    val playEvent: PlayEventEntity,

    @JoinColumn(name = "subject_id", nullable = false)
    @ManyToOne
    val subject: SubjectEntity,
) : BaseEntity()