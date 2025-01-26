package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.answer.AnswerEntity
import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.validation.constraints.NotNull

data class AnswerQuestionReq(
    @field:NotNull
    val questionId: Int,

    @field:NotNull
    val answer: String
) {
    fun toEntity(
        user: UserEntity,
        question: QuestionEntity,
    ) = AnswerEntity(
        answer = answer,
        user = user,
        question = question
    )
}