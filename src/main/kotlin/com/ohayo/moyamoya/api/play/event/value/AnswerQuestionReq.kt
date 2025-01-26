package com.ohayo.moyamoya.api.play.event.value

import jakarta.validation.constraints.NotNull

data class AnswerQuestionReq(
    @field:NotNull
    val questionId: Int,

    @field:NotNull
    val content: String
)