package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.TestAnnotation
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@TestAnnotation
class SchoolApiTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `학교 조회`() {
        mvc.get("/schools") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpectAll {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }
    }
}