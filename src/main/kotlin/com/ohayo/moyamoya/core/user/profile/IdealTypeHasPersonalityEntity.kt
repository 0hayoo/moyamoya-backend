package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_ideal_type_has_personality")
class IdealTypeHasPersonalityEntity(
    @JoinColumn(nullable = false)
    @ManyToOne
    val idealType: IdealTypeEntity,
    
    @JoinColumn(nullable = false)
    @ManyToOne
    val personality: PersonalityEntity
) : BaseEntity()