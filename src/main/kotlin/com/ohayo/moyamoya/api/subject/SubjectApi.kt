package com.ohayo.moyamoya.api.subject

import com.ohayo.moyamoya.api.subject.value.CreateSubjectReq
import com.ohayo.moyamoya.core.question.SubjectEntity
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subjects")
class SubjectApi(
    private val subjectService: SubjectService
) {
    @GetMapping
    fun getAllSubjects(): List<SubjectEntity> = subjectService.getAllSubjects()

    @PostMapping
    fun createSubject(
        @RequestBody @Valid req: CreateSubjectReq
    ) = subjectService.createSubject(req)
}