package com.ohayo.moyamoya.global

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class GlobalConfig {
    @Bean
    @Qualifier("discord")
    fun discordRestClient() = RestClient.builder()
        .baseUrl("https://discord.com/api/webhooks/1325832523313578135/O0PtD9baF8cuPhid2dBDACIjH-uAleq_Mk56c4coc1IqF-mDG8zwAnvv8apyoUtzIfMR")
        .build()

    @Bean
    fun logger() = KotlinLogging.logger { }
}