package com.ohayo.moyamoya.infra.discord

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class DiscordErrorSendService(
    @Qualifier("discord")
    private val restClient: RestClient
) {
    fun sendDiscordAlarm(e: Exception, request: WebRequest) {
        restClient.post()
            .body(createMessage(e, request))
            .retrieve()
            .body<Any>()
    }

    private fun createMessage(e: Exception, request: WebRequest): DiscordMessage {
        return DiscordMessage(
            content = "# \uD83D\uDEA8 에러 발생",
            embeds = listOf(
                Embed(
                    title = "\uD83D\uDEA8️ 에러 정보",
                    description = "### 🕖 발생 시간\n"
                            + LocalDateTime.now()
                            + "\n"
                            + "### 🔗 요청 URL\n"
                            + createRequestFullPath(request)
                            + "\n"
                            + "### 📄 Stack Trace\n"
                            + "```\n"
                            + getStackTrace(e).substring(0, 2000)
                            + "\n```"
                )
            )
        )
    }

    private fun createRequestFullPath(webRequest: WebRequest): String {
        val request = (webRequest as ServletWebRequest).request
        var fullPath = "${request.method} ${request.requestURL}"

        val queryString = request.queryString
        if (!queryString.isNullOrEmpty()) {
            fullPath += "?$queryString"
        }

        return fullPath
    }

    private fun getStackTrace(e: Exception): String = StringWriter().apply {
        e.printStackTrace(PrintWriter(this))
    }.toString()
}