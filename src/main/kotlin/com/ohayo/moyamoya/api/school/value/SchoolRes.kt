package com.ohayo.moyamoya.api.school.value

import com.ohayo.moyamoya.core.school.SchoolType
import java.time.LocalDate

data class SchoolRes(
    val id: Int,
    val name: String,
    val type: SchoolType?,
    val cityName: String,
    val postalCode: String?,
    val address: String?,
    val addressDetail: String?,
    val phone: String,
    val website: String?,
    val foundedAt: LocalDate,
    val anniversary: LocalDate,
    val schoolCode: String,
    val officeCode: String,
    val studentCount: Int
)