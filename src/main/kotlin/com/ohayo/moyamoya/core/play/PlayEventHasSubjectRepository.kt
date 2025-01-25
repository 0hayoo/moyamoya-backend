package com.ohayo.moyamoya.core.play

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayEventHasSubjectRepository : JpaRepository<PlayEventHasSubjectEntity, Int> {
    fun findByPlayEvent(playEvent: PlayEventEntity): List<PlayEventHasSubjectEntity>
}