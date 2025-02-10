package com.ohayo.moyamoya

import com.ohayo.moyamoya.legacy.DatabaseCleanUpExtension
import com.ohayo.moyamoya.legacy.GlobalSetupExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@AutoConfigureTestDatabase(
    connection = EmbeddedDatabaseConnection.H2,
    replace = AutoConfigureTestDatabase.Replace.ANY
)
@TestPropertySource(properties = ["spring.config.location = classpath:application-test.yml"])
// 순서 주의: DB 초기화 후 DB 설정
@AutoConfigureMockMvc
@ExtendWith(DatabaseCleanUpExtension::class, GlobalSetupExtension::class)
annotation class MockMvcTest