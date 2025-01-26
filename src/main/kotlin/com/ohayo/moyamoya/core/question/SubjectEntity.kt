package com.ohayo.moyamoya.core.question

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_subject")
class SubjectEntity(
    @Column(nullable = false)
    val title: String,
) : BaseEntity()