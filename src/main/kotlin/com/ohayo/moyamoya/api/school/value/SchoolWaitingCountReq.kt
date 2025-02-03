package com.ohayo.moyamoya.api.school.value

data class SchoolWaitingCountReq (
    val schoolId: Int,
    val waitCount: Long,
    val hashValue: String,
    val nonce: String
)