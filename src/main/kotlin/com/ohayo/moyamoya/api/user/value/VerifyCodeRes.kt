package com.ohayo.moyamoya.api.user.value

import com.ohayo.moyamoya.infra.token.Token

data class VerifyCodeRes(
    val isNewUser: Boolean,
    val token: Token?
)
