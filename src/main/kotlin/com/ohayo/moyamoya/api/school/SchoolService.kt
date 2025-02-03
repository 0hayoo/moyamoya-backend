package com.ohayo.moyamoya.api.school

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.school.util.SchoolValidator
import com.ohayo.moyamoya.api.school.value.SchoolRes
import com.ohayo.moyamoya.api.school.value.SchoolWaitingCountReq
import com.ohayo.moyamoya.api.school.value.SchoolWaitingCountRes
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.core.school.SchoolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SchoolService(
    private val schoolRepository: SchoolRepository,
) {
    fun getSchools(): List<SchoolRes> = schoolRepository.findAllResWithStudentCount()

    fun getSchoolWaitingCount(): List<SchoolWaitingCountRes> = schoolRepository.findAllWaitingCountRes()

    fun addSchoolWaitingCount(req: SchoolWaitingCountReq): VoidRes = VoidRes().apply {
        SchoolValidator.validateSchoolWaitingCount(req.waitCount)

        schoolRepository.findByIdSafety(req.schoolId).apply {
            modifyWaitingCount(req.waitCount)
        }.let {
            schoolRepository.save(it)
        }
    }

}