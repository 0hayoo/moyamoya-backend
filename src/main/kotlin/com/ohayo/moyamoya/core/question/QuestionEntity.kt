package com.ohayo.moyamoya.core.question

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.answer.AnswerEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_question")
class QuestionEntity(
    @Column(nullable = false)
    val question: String,
    
    @JoinColumn(name = "answer_id")
    @OneToOne(fetch = FetchType.LAZY)
    val answer: AnswerEntity? = null,

    @JoinColumn(name = "subject_id", nullable = false)
    @ManyToOne(cascade = [CascadeType.PERSIST])
    val subject: SubjectEntity
) : BaseEntity()