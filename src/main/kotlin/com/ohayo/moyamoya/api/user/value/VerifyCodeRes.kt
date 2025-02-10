package com.ohayo.moyamoya.api.user.value

import com.ohayo.moyamoya.infra.token.Token

data class VerifyCodeRes(
    val isNewUser: Boolean,
    val token: Token?
) {
    companion object {
        fun of(token: Token?) = VerifyCodeRes(
            isNewUser = token == null,
            token = token
        )
    }
}