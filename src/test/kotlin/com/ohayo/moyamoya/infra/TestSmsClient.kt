package com.ohayo.moyamoya.infra

import com.ohayo.moyamoya.infra.sms.SmsClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class TestSmsClient: SmsClient {
    companion object {
        const val FAKE_AUTHORIZATION_CODE = "123456" 
    }
    override fun sendAuthorizationCode(phone: String): String {
        return FAKE_AUTHORIZATION_CODE
    }

    override fun sendText(phone: String, text: String) {
        
    }
}