package com.ohayo.moyamoya.core.question

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<QuestionEntity, Int> {
    fun findBySubject(subject: SubjectEntity): List<QuestionEntity>
}