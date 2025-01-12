package com.ohayo.moyamoya.infra.neis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("neis")
class NeisProperties @ConstructorBinding constructor(
    val apiKey: String
)