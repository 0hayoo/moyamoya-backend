package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.TestAnnotation
import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SignUpReq
import com.ohayo.moyamoya.common.GlobalState
import com.ohayo.moyamoya.common.toJson
import com.ohayo.moyamoya.core.Gender
import com.ohayo.moyamoya.infra.TestSmsClient
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@TestAnnotation
class UserApiTest {

    @Autowired
    lateinit var mvc: MockMvc

    val dummyPhone = "01012345678"

    @Test
    fun `계정 존재여부 확인 - 존재하지 않음`() {
        mvc.get("/users/exists") {
            param("phone", dummyPhone)
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("$.isExists", equalTo(false))
                contentType(MediaType.APPLICATION_JSON)
            }
        }
    }

    @Test
    fun `계정 존재여부 확인 - 존재함`() {
        `회원 가입`()
        mvc.get("/users/exists") {
            param("phone", dummyPhone)
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("$.isExists", equalTo(true))
                contentType(MediaType.APPLICATION_JSON)
            }
        }
    }

    @Test
    fun `인증 코드 발송`() {
        mvc.post("/users/authorization-code") {
            param("phone", dummyPhone)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `인증 코드 발송 후 검증`() {
        `인증 코드 발송`()
        mvc.post("/users/authorize-code") {
            param("phone", dummyPhone)
            param("code", TestSmsClient.FAKE_AUTHORIZATION_CODE)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `회원 가입`() {
        mvc.post("/users/sign-up") {
            val id = GlobalState.school!!.id
            content = SignUpReq(
                phone = "testPhone",
                schoolId = id,
                schoolGrade = 1,
                schoolClass = 1,
                name = "testName",
                gender = Gender.MALE,
                password = "testPassword",
                profileImageUrl = "testProfileImageUrl",
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `토큰 리프레쉬`() {
        mvc.post("/users/refresh") {
            content = RefreshReq(
                refreshToken = GlobalState.token!!.refreshToken
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `토큰 리프레쉬 - 이상한 토큰`() {
        mvc.post("/users/refresh") {
            content = RefreshReq(
                refreshToken = "wow"
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isUnauthorized() } }
            .andDo { print() }
    }

    @Test
    fun `내 정보 조회`() {
        mvc.get("/users") {
            header("Authorization", "Bearer ${GlobalState.token!!.accessToken}")
        }.andExpect { status { isOk() } }
    }
}