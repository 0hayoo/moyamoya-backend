package com.ohayo.moyamoya.infra.discord

data class DiscordMessage(
    val content: String = "",
    val embeds: List<Embed> = emptyList(),
)