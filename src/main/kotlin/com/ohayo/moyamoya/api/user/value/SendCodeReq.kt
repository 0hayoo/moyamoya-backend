package com.ohayo.moyamoya.api.user.value

import jakarta.validation.constraints.NotNull

data class SendCodeReq(
    @field:NotNull
    val phone: String,
)
