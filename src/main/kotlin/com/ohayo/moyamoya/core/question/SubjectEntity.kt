package com.ohayo.moyamoya.core.question

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_subject")
class SubjectEntity(
    @Column(nullable = false)
    val subject: String
): BaseEntity()