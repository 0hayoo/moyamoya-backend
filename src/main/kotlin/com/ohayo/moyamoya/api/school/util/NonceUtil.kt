package com.ohayo.moyamoya.api.school.util

import com.ohayo.moyamoya.api.school.NonceProperties
import com.ohayo.moyamoya.global.CustomException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.security.SecureRandom

@Component
class NonceUtil(
    private val nonceProperties: NonceProperties
) {
    private val secureRandom = SecureRandom()

    fun generateSchoolWaitingNonce(): Int = secureRandom.nextInt(900000) + 100000

    fun matchNonce(nonce: String, hashValue: String) {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest((nonceProperties.key + nonce).toByteArray())
        if(hashBytes.joinToString("") { "%02x".format(it) } != hashValue) {
            throw CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "잘못된 값입니다.")
        }
    }
}