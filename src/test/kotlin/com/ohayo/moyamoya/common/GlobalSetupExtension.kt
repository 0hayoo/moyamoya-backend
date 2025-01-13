package com.ohayo.moyamoya.common

import com.ohayo.moyamoya.core.*
import com.ohayo.moyamoya.infra.token.JwtClient
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class GlobalSetupExtension: BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        val applicationContext = SpringExtension.getApplicationContext(context)
        val userRepository = applicationContext.getBean(UserRepository::class.java)
        val schoolRepository = applicationContext.getBean(SchoolRepository::class.java)
        val jwtClient = applicationContext.getBean(JwtClient::class.java)
        GlobalState.initToken(userRepository, schoolRepository, jwtClient)
    }
}