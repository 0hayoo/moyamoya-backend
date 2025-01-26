package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.answer.AnswerEntity

data class AnswerRes(
    val content: String,
    val questionId: Int
) {
    companion object {
        fun of(entity: AnswerEntity) = AnswerRes(
            content = entity.content,
            questionId = entity.question.id
        )
    }
}