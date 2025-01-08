package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.core.SchoolEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("schools")
class SchoolApi(
    private val schoolService: SchoolService
) {
    @GetMapping
    fun getSchools(): List<SchoolEntity> = schoolService.getSchools()
}