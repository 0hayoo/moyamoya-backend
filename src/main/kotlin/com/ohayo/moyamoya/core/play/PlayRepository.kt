package com.ohayo.moyamoya.core.play

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayRepository : JpaRepository<PlayEntity, Int>

fun PlayRepository.findByNonExpired(userId: Int) = findAll().firstOrNull {
    !it.isExpired && (it.male.user.id == userId || it.female.user.id == userId)
}