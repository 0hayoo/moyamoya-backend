package com.ohayo.moyamoya.api.school

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.school.value.SchoolRes
import com.ohayo.moyamoya.api.school.value.SchoolWaitingCountReq
import com.ohayo.moyamoya.api.school.value.SchoolWaitingCountRes
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("schools")
class SchoolApi(
    private val schoolService: SchoolService
) {
    @GetMapping
    fun getSchools(): List<SchoolRes> = schoolService.getSchools()

    @GetMapping("waiting")
    fun getWaitingCount(): List<SchoolWaitingCountRes> = schoolService.getSchoolWaitingCount()

    @PostMapping("waiting")
    fun waitingSchool(@RequestBody req: SchoolWaitingCountReq): VoidRes = schoolService.addSchoolWaitingCount(req)
}