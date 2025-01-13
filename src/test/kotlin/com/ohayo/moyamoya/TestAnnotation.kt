package com.ohayo.moyamoya
import com.ohayo.moyamoya.common.GlobalSetupExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // reset DB
@AutoConfigureTestDatabase(
    connection = EmbeddedDatabaseConnection.H2,
    replace = AutoConfigureTestDatabase.Replace.ANY
)
@TestPropertySource(properties = ["spring.config.location = classpath:application-test.yml"])
@AutoConfigureMockMvc
@ExtendWith(GlobalSetupExtension::class)
annotation class TestAnnotation