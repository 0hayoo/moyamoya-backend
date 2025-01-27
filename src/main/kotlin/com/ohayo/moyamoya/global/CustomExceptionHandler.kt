package com.ohayo.moyamoya.global

import com.ohayo.moyamoya.infra.discord.DiscordErrorSendService
import mu.KLogger
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class CustomExceptionHandler(
    private val logger: KLogger,
    private val environment: Environment,
    private val discordErrorSendService: DiscordErrorSendService
) {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ErrorRes> {
        logger.error("CustomExceptionHandler.CustomException", exception)
        return createErrorResponse(
            status = exception.status,
            message = exception.message
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(exception: NoResourceFoundException): ResponseEntity<ErrorRes> {
        logger.error("CustomExceptionHandler.NoResourceFoundException", exception)
        return createErrorResponse(
            status = HttpStatus.NOT_FOUND,
            message = HttpStatus.NOT_FOUND.reasonPhrase
        )
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupported(exception: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorRes> {
        logger.error("CustomExceptionHandler.HttpRequestMethodNotSupportedException", exception)
        return createErrorResponse(
            status = HttpStatus.METHOD_NOT_ALLOWED,
            message = HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception, webRequest: WebRequest): ResponseEntity<ErrorRes> {
        logger.error("CustomExceptionHandler.Exception", exception)

        if (environment.activeProfiles.contains("prd")) {
            discordErrorSendService.sendDiscordAlarm(exception, webRequest)
        }

        return createErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
        )
    }

    private fun createErrorResponse(
        status: HttpStatus,
        message: String,
    ) = ResponseEntity.status(status).body(

        ErrorRes(
            status = status.value(),
            message = message
        )
    )
}