package com.ohayo.moyamoya.infra.sms

interface SmsClient {
    fun sendAuthorizationCode(phone: String): String
    fun sendText(phone: String, text: String)
}