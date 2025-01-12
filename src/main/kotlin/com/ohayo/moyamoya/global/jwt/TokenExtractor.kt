package com.ohayo.moyamoya.global.jwt
import com.ohayo.moyamoya.global.CustomException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus

object TokenExtractor {
    fun extract(request: HttpServletRequest): String? {
        val authorization = request.getHeader("Authorization") ?: return null
        return token(authorization)
    }

    private fun token(authorization: String): String {
        if (!authorization.startsWith("Bearer ")) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "token does not start with Bearer")
        }
        return authorization.removePrefix("Bearer ")
    }
}