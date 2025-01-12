package com.ohayo.moyamoya.api.user.value

import com.ohayo.moyamoya.core.Gender

data class SignUpRequest(
    val tel: String,
    val schoolId: Int,
    val schoolGrade: Int,
    val schoolClass: Int,
    val name: String,
    val gender: Gender,
    val password: String,
    val profileImageUrl: String,
)