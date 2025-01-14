package com.ohayo.moyamoya.api.user.value

import jakarta.validation.constraints.NotNull

data class VerifyCodeReq(
    @field:NotNull
    val phone: String,
    
    @field:NotNull
    val code: String
)
