package com.ohayo.moyamoya.core.play

import com.ohayo.moyamoya.core.question.SubjectEntity
import com.ohayo.moyamoya.global.CustomException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

@Repository
interface PlayEventRepository : JpaRepository<PlayEventEntity, Int> {
    fun findByPlay(play: PlayEntity): List<PlayEventEntity>
    fun findBySubject(subject: SubjectEntity): PlayEventEntity?
}

fun PlayEventRepository.findBySubjectSafety(subject: SubjectEntity) =
    findBySubject(subject) ?: throw CustomException(HttpStatus.NOT_FOUND)