package com.ohayo.moyamoya.api.subject.value

import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.question.SubjectEntity
import jakarta.validation.constraints.NotNull

data class CreateQuestionReq(
    @field:NotNull
    val question: String
) {
    fun toEntity(subject: SubjectEntity) = QuestionEntity(
        question = question,
        subject = subject
    )
}