package com.ohayo.moyamoya.global

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import mu.KLogger
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class ErrorResponseSender(
    private val objectMapper: ObjectMapper
) {
    companion object : KLogging()

    fun send(response: HttpServletResponse, customException: CustomException) {
        println("customException: $customException")
        send(response, customException.status, customException.message)
    }

    fun send(response: HttpServletResponse, status: HttpStatus, message: String? = null) {
        println(response)
        try {
            response.apply {
                this.status = status.value()
                contentType = MediaType.APPLICATION_JSON_VALUE
                characterEncoding = "UTF-8"
            }
            response.writer.write(
                objectMapper.writeValueAsString(
                    ErrorRes(
                        status = status.value(),
                        code = 0,
                        message = message ?: status.reasonPhrase
                    )
                )
            )
        } catch (e: IOException) {
            logger.error("ErrorResponseSender.send", e)
        }
    }
}