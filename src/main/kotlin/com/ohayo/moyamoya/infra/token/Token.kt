package com.ohayo.moyamoya.infra.token

data class Token(
    val accessToken: String,
    val refreshToken: String
)