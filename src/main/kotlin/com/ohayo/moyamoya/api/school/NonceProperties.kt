package com.ohayo.moyamoya.api.school

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("nonce")
class NonceProperties @ConstructorBinding constructor(
    val key: String
)