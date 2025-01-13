package com.ohayo.moyamoya.api.user.value

import jakarta.validation.constraints.NotNull

data class RefreshReq(
    @field:NotNull
    val refreshToken: String,
)
