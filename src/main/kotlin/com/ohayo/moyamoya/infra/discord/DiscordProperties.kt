package com.ohayo.moyamoya.infra.discord

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("discord")
class DiscordProperties @ConstructorBinding constructor(
    val webhookUrl: String
)