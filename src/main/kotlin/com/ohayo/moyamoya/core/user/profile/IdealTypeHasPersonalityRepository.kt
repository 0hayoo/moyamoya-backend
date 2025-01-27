package com.ohayo.moyamoya.core.user.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IdealTypeHasPersonalityRepository : JpaRepository<IdealTypeHasPersonalityEntity, Int> {
    fun findByIdealType(idealType: IdealTypeEntity): List<IdealTypeHasPersonalityEntity>
    fun removeByIdealType(idealType: IdealTypeEntity)
}