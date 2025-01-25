package com.ohayo.moyamoya.api.subject

import com.ohayo.moyamoya.api.subject.value.CreateSubjectReq
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.question.SubjectEntity
import com.ohayo.moyamoya.core.question.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val questionRepository: QuestionRepository
) {
    fun getAllSubjects(): List<SubjectEntity> = subjectRepository.findAll()

    fun createSubject(req: CreateSubjectReq) {
        val subject = subjectRepository.save(req.toSubjectEntity())
        questionRepository.saveAll(req.toQuestionEntities(subject))
    }
}