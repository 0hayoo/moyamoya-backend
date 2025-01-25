package com.ohayo.moyamoya.api.subject.value

import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.question.SubjectEntity
import jakarta.validation.constraints.NotNull

data class CreateSubjectReq(
    @field:NotNull
    val subject: String,
    @field:NotNull
    val questions: List<CreateQuestionReq>
) {
    fun toEntity(): SubjectEntity {
        val questions = arrayListOf<QuestionEntity>()
        val subject = SubjectEntity(
            subject = this.subject,
            questions = questions
        )
        questions.addAll(this.questions.map {
            it.toEntity(subject)
        })

        return subject
    }
}