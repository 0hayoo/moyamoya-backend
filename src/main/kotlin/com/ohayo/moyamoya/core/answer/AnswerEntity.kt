package com.ohayo.moyamoya.core.answer

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.play.PlayEventEntity
import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_answer")
class AnswerEntity(
    @Column(nullable = false)
    val answer: String,

    @JoinColumn(nullable = false)
    @ManyToOne
    val user: UserEntity,

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val playEvent: PlayEventEntity,

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val question: QuestionEntity
) : BaseEntity()