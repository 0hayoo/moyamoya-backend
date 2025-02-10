package com.ohayo.moyamoya.common

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class DatabaseCleanUpExtension: BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        val applicationContext = SpringExtension.getApplicationContext(context)
        val databaseCleanUp = applicationContext.getBean(DatabaseCleanUp::class.java)
        databaseCleanUp.execute()
    }
}