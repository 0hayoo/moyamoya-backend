package com.ohayo.moyamoya.global

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("discord")
class DiscordProperties @ConstructorBinding constructor(
    val webhookUrl: String
)

@ConfigurationProperties("neis")
class NeisProperties @ConstructorBinding constructor(
    val apiKey: String
)