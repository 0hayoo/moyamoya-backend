package com.ohayo.moyamoya.core.answer

import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository : JpaRepository<AnswerEntity, Int> {
    fun findByUserAndQuestionIn(user: UserEntity, question: List<QuestionEntity>): List<AnswerEntity>
}