package com.ohayo.moyamoya.api.subject

import com.ohayo.moyamoya.api.subject.value.CreateSubjectReq
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.question.SubjectEntity
import com.ohayo.moyamoya.core.question.SubjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SubjectService(
    private val subjectRepository: SubjectRepository
) {
    fun getAllSubjects(): List<SubjectEntity> = subjectRepository.findAll()

    fun createSubject(req: CreateSubjectReq) {
        subjectRepository.save(req.toEntity())
    }
}