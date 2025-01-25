package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.question.SubjectEntity

data class SubjectRes(
    val subject: String,
    val questions: List<QuestionRes>
) {
    companion object {
        fun of(entity: SubjectEntity) = SubjectRes(
            subject = entity.subject,
            questions = entity.questions.map(QuestionRes::of)
        )
    }
}
