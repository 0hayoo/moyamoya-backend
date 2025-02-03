package com.ohayo.moyamoya.api.school.value

data class SchoolWaitingCountRes(
    val schoolId: Int,
    val name: String,
    val waitingCount: Long
)