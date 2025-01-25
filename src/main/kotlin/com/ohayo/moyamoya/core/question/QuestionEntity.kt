package com.ohayo.moyamoya.core.question

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_question")
class QuestionEntity(
    @Column(nullable = false)
    val question: String,
    
    @JoinColumn(name = "subject_id", nullable = false)
    @ManyToOne
    val subject: SubjectEntity
) : BaseEntity()