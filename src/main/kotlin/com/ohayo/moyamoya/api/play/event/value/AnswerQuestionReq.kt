package com.ohayo.moyamoya.api.play.event.value

import jakarta.validation.constraints.NotNull

data class AnswerQuestionReq(
    @field:NotNull
    val answer: String
)
