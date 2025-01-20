package com.ohayo.moyamoya.api.school

import com.ohayo.moyamoya.api.school.value.SchoolRes
import com.ohayo.moyamoya.core.SchoolEntity
import com.ohayo.moyamoya.core.SchoolRepository
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private val schoolRepository: SchoolRepository
) {
    fun getSchools(): List<SchoolRes> = schoolRepository.findAllWithStudentCount()
}