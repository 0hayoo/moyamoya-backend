package com.ohayo.moyamoya.infra.sms

import net.nurigo.java_sdk.api.Message
import net.nurigo.java_sdk.exceptions.CoolsmsException
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class SmsClientImpl(
    coolSMSProperties: CoolSMSProperties
) : SmsClient {
    val coolsms = Message(coolSMSProperties.apiKey, coolSMSProperties.apiSecret)
    val senderPhone = coolSMSProperties.senderPhone

    override fun sendAuthorizationCode(phone: String): String {
        val authorizationCode = ((Math.random() * (999999 - 100000 + 1)).toInt() + 100000).toString()
        val text = "[모야모야]\n인증번호: $authorizationCode\n인증 번호를 입력해주세요."

        sendText(phone = phone, text = text)

        return authorizationCode
    }

    override fun sendText(phone: String, text: String) {
        try {
            coolsms.send(
                hashMapOf(
                    "to" to phone,
                    "from" to senderPhone,
                    "type" to "SMS",
                    "text" to text
                )
            )
        } catch (e: CoolsmsException) {
            throw RuntimeException(e)
        }
    }
}