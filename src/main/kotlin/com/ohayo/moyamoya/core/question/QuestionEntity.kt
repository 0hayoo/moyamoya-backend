package com.ohayo.moyamoya.core.question

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_question")
class QuestionEntity(
    @Column(nullable = false)
    val question: String,

    @JoinColumn(name = "subject_id", nullable = false)
    @ManyToOne(cascade = [CascadeType.PERSIST])
    val subject: SubjectEntity
) : BaseEntity()