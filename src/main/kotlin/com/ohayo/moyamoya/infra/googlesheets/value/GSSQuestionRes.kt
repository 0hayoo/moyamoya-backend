package com.ohayo.moyamoya.infra.googlesheets.value

import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.question.SubjectEntity

data class GSSQuestionRes(
    val question: String
) {
    fun toEntity(subject: SubjectEntity) = QuestionEntity(
        content = question,
        subject = subject
    )
}