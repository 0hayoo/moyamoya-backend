package com.ohayo.moyamoya.core.user.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonalityRepository : JpaRepository<PersonalityEntity, Int> {
    fun findByIdIn(ids: List<Int>): List<PersonalityEntity>
}