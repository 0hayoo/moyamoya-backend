package com.ohayo.moyamoya.core.play

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayEventRepository : JpaRepository<PlayEventEntity, Int> {
    fun findByPlay(play: PlayEntity): List<PlayEventEntity>
}