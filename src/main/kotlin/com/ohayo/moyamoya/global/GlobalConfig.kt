package com.ohayo.moyamoya.global

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class GlobalConfig(
    private val discordProperties: DiscordProperties
) {
    @Bean
    @Qualifier("discord")
    fun discordRestClient() = RestClient.builder()
        .baseUrl(discordProperties.webhookUrl)
        .build()
    
    @Bean
    @Qualifier("neis")
    fun neisRestClient() = RestClient.builder()
        .baseUrl("https://open.neis.go.kr")
        .build()

    @Bean
    fun logger() = KotlinLogging.logger { }
}