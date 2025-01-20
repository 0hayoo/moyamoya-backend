package com.ohayo.moyamoya.api.user.value

import com.ohayo.moyamoya.core.user.Gender
import jakarta.validation.constraints.NotNull

data class SignUpReq(
    @field:NotNull
    val phone: String,

    @field:NotNull
    val schoolId: Int,

    @field:NotNull
    val schoolGrade: Int,

    @field:NotNull
    val schoolClass: Int,

    @field:NotNull
    val name: String,

    @field:NotNull
    val gender: Gender,

    @field:NotNull
    val profileImageUrl: String,

    @field:NotNull
    val code: String
)