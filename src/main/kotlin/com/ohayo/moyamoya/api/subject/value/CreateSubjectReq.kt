package com.ohayo.moyamoya.api.subject.value

import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.question.SubjectEntity

data class CreateSubjectReq(
    val subject: String,
    val questions: List<CreateQuestionReq>
) {
    fun toSubjectEntity() = SubjectEntity(
        subject = subject,
    )
    
    fun toQuestionEntities(subject: SubjectEntity) = questions.map { 
        QuestionEntity(
            subject = subject,
            question = it.question
        )
    }
}