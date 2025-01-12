package com.ohayo.moyamoya.infra.token

enum class JwtPayloadKey(
    val key: String
) {
    ID("id"),
    TEL("email"),
    ROLE("role");
}