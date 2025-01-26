package com.ohayo.moyamoya.api.school

import com.ohayo.moyamoya.api.school.value.SchoolRes
import com.ohayo.moyamoya.core.school.SchoolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SchoolService(
    private val schoolRepository: SchoolRepository
) {
    fun getSchools(): List<SchoolRes> = schoolRepository.findAllResWithStudentCount()
}