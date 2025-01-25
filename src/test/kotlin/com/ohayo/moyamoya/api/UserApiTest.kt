package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.TestAnnotation
import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SendCodeReq
import com.ohayo.moyamoya.api.user.value.SignUpReq
import com.ohayo.moyamoya.api.user.value.VerifyCodeReq
import com.ohayo.moyamoya.common.GlobalState
import com.ohayo.moyamoya.common.toJson
import com.ohayo.moyamoya.core.user.Gender
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

    val testPhone2 = "testPhone2"

    @Test
    fun `인증 코드 발송`() {
        mvc.post("/users/send-code") {
            content = SendCodeReq(
                phone = testPhone2,
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
    }

    @Test
    fun `인증 코드 발송 후 검증`() {
        this.`인증 코드 발송`()
        mvc.post("/users/verify-code") {
            content = VerifyCodeReq(
                phone = testPhone2,
                code = TestSmsClient.FAKE_AUTHORIZATION_CODE
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.isNewUser", equalTo(true))
        }
    }

    @Test
    fun `회원 가입 후 재 인증`() {
        this.`회원 가입`()
        this.`인증 코드 발송`()
        mvc.post("/users/verify-code") {
            content = VerifyCodeReq(
                phone = testPhone2,
                code = TestSmsClient.FAKE_AUTHORIZATION_CODE
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.isNewUser", equalTo(false))
        }.andDo { print() }
    }

    @Test
    fun `인증 코드 발송 후 검증 - 이상한 코드`() {
        this.`인증 코드 발송`()
        mvc.post("/users/verify-code") {
            content = VerifyCodeReq(
                phone = testPhone2,
                code = "노영재"
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `회원 가입`() {
        this.`인증 코드 발송 후 검증`()
        mvc.post("/users/sign-up") {
            val id = GlobalState.school!!.id
            content = SignUpReq(
                phone = testPhone2,
                schoolId = id,
                schoolGrade = 1,
                schoolClass = 1,
                name = "testName",
                gender = Gender.MALE,
                profileImageUrl = "testProfileImageUrl",
                code = TestSmsClient.FAKE_AUTHORIZATION_CODE
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
    }

    @Test
    fun `회원 가입 - 이상한 인증 코드`() {
        this.`인증 코드 발송 후 검증`()
        mvc.post("/users/sign-up") {
            val id = GlobalState.school!!.id
            content = SignUpReq(
                phone = testPhone2,
                schoolId = id,
                schoolGrade = 1,
                schoolClass = 1,
                name = "testName",
                gender = Gender.MALE,
                profileImageUrl = "testProfileImageUrl",
                code = "노영재"
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `토큰 리프레쉬`() {
        mvc.post("/users/refresh") {
            content = RefreshReq(
                refreshToken = GlobalState.token!!.refreshToken
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
    }

    @Test
    fun `토큰 리프레쉬 - 이상한 토큰`() {
        mvc.post("/users/refresh") {
            content = RefreshReq(
                refreshToken = "wow"
            ).toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isUnauthorized() } }
    }

    @Test
    fun `내 정보 조회`() {
        mvc.get("/users") {
            header("Authorization", "Bearer ${GlobalState.token!!.accessToken}")
        }.andExpect { status { isOk() } }
    }
}