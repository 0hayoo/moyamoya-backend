package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.answer.AnswerEntity

data class AnswersRes(
    val maleAnswer: List<AnswerRes>,
    val femaleAnswer: List<AnswerRes>
) {
    companion object {
        fun of(
            maleAnswers: List<AnswerEntity>,
            femaleAnswers: List<AnswerEntity>
        ) = AnswersRes(
            maleAnswer = maleAnswers.map(AnswerRes::of),
            femaleAnswer = femaleAnswers.map(AnswerRes::of)
        )
    }
}