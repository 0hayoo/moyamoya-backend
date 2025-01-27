package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_personality")
class PersonalityEntity(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val toMBTI: PersonalityToMBTI,
    
    @Column(nullable = false, unique = true)
    val content: String
) : BaseEntity()

enum class PersonalityToMBTI {
    ST,
    SF,
    NT,
    NF
}