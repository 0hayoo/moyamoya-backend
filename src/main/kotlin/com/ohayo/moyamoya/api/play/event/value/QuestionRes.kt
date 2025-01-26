package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.question.QuestionEntity

data class QuestionRes(
    val id: Int,
    val question: String
) {
    companion object {
        fun of(entity: QuestionEntity) = QuestionRes(
            id = entity.id,
            question = entity.question
        )
    }
}
