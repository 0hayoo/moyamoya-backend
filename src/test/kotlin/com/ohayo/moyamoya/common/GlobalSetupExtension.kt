package com.ohayo.moyamoya.common

import com.ohayo.moyamoya.core.school.SchoolRepository
import com.ohayo.moyamoya.core.user.UserRepository
import com.ohayo.moyamoya.infra.token.JwtClient
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class GlobalSetupExtension: BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        val applicationContext = SpringExtension.getApplicationContext(context)
        val userRepository = applicationContext.getBean(UserRepository::class.java)
        val schoolRepository = applicationContext.getBean(SchoolRepository::class.java)
        val jwtClient = applicationContext.getBean(JwtClient::class.java)
        GlobalState.initToken(userRepository, schoolRepository, jwtClient)
    }
}