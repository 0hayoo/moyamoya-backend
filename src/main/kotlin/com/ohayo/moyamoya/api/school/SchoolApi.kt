package com.ohayo.moyamoya.api.school

import com.ohayo.moyamoya.api.school.value.SchoolRes
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("schools")
class SchoolApi(
    private val schoolService: SchoolService
) {
    @GetMapping
    fun getSchools(): List<SchoolRes> = schoolService.getSchools()
    
}