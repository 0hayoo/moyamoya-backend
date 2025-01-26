package com.ohayo.moyamoya.core.answer

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_answer")
class AnswerEntity(
    @Column(nullable = false)
    val answer: String,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: UserEntity,

    @JoinColumn(name = "question_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val question: QuestionEntity
) : BaseEntity()