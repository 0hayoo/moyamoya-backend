package com.ohayo.moyamoya.api.subject.value

import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.question.SubjectEntity

data class CreateQuestionReq(
    val question: String
) {
    fun toEntity(subject: SubjectEntity) = QuestionEntity(
        question = question,
        subject = subject
    )
}