package com.ohayo.moyamoya.core.answer

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "tbl_answer",
    uniqueConstraints = [
        UniqueConstraint(
            name = "UniqueUserAndQuestion",
            columnNames = ["user_id", "question_id"]
        )
    ]
)
class AnswerEntity(
    @Column(nullable = false)
    val content: String,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: UserEntity,

    @JoinColumn(name = "question_id", nullable = false)
    @ManyToOne
    val question: QuestionEntity
) : BaseEntity()