package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.school.SchoolFixture
import com.ohayo.moyamoya.api.user.value.SendCodeReq
import com.ohayo.moyamoya.api.user.value.SignUpReq
import com.ohayo.moyamoya.api.user.value.VerifyCodeReq
import com.ohayo.moyamoya.core.phonecode.PhoneCodeEntity
import com.ohayo.moyamoya.core.phonecode.PhoneCodeEntityFixture
import com.ohayo.moyamoya.core.phonecode.PhoneCodeRepository
import com.ohayo.moyamoya.core.school.SchoolRepository
import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.user.UserRepository
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import com.ohayo.moyamoya.infra.sms.SmsClient
import com.ohayo.moyamoya.infra.token.JwtClient
import com.ohayo.moyamoya.infra.token.TokenFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class UserServiceTest {
    @InjectMockKs
    lateinit var userService: UserService

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var schoolRepository: SchoolRepository

    @MockK
    lateinit var jwtClient: JwtClient

    @MockK
    lateinit var smsClient: SmsClient

    @MockK
    lateinit var phoneCodeRepository: PhoneCodeRepository

    @MockK
    lateinit var sessionHolder: UserSessionHolder
    
    @BeforeEach
    fun beforeEach() {
        every { jwtClient.generate(any()) } returns TokenFixture.token1
    }
    
    @Nested
    inner class SendCode {
        private val req = SendCodeReq(
            phone = "010-1234-5678"
        )
        
        @Test
        fun success() {
            // given
            every { smsClient.sendAuthorizationCode(any()) } returns "123456"
            every { phoneCodeRepository.save(any()) } returnsArgument 0

            // when
            userService.sendCode(req)

            // then
            verify { phoneCodeRepository.save(any()) }
        }
    }

    @Nested
    inner class VerifyCode {
        private val req = VerifyCodeReq(
            phone = PhoneCodeEntityFixture.entity.phone,
            code = PhoneCodeEntityFixture.entity.code
        )

        @Test
        fun failure_whenEmptyCodeList() {
            // given
            every { phoneCodeRepository.findByStatusAndPhoneAndCode(any(), any(), any()) } returns listOf()

            // when
            val exception = assertThrows<CustomException> {
                userService.verifyCode(req)
            }
            // then
            assertEquals(HttpStatus.BAD_REQUEST, exception.status)
        }

        @Test
        fun success_newUser() {
            // given
            every { phoneCodeRepository.findByStatusAndPhoneAndCode(any(), any(), any()) } returns
                    listOf(PhoneCodeEntityFixture.entity)
            every { phoneCodeRepository.saveAll(any<List<PhoneCodeEntity>>()) } returnsArgument 0
            every { userRepository.findByPhone(any()) } returns null

            // when
            val res = userService.verifyCode(req)

            // then
            assertEquals(true, res.isNewUser)
            assertEquals(null, res.token)
        }

        @Test
        fun success_existUser() {
            // given
            every { phoneCodeRepository.findByStatusAndPhoneAndCode(any(), any(), any()) } returns
                    listOf(PhoneCodeEntityFixture.entity)
            every { phoneCodeRepository.saveAll(any<List<PhoneCodeEntity>>()) } returnsArgument 0
            every { userRepository.findByPhone(any()) } returns UserFixture.user1

            // when
            val res = userService.verifyCode(req)

            // then
            assertEquals(false, res.isNewUser)
            assertNotNull(res.token)
        }
    }

    @Nested
    inner class SignUp {
        private val req = SignUpReq(
            phone = "010-1234-5678",
            schoolId = 1,
            schoolGrade = 1,
            schoolClass = 1,
            name = "1",
            gender = Gender.MALE,
            profileImageUrl = "",
            code = "123456"
        )

        @Test
        fun failure_whenAlreadyExistPhone() {
            // given
            every { userRepository.existsByPhone(any()) } returns true

            // when
            val exception = assertThrows<CustomException> {
                userService.signUp(req)
            }

            // then
            assertEquals(HttpStatus.BAD_REQUEST, exception.status)
            assertEquals(0, exception.code)
        }

        @Test
        fun failure_whenEmptyCodeList() {
            // given
            every { userRepository.existsByPhone(any()) } returns false
            every { phoneCodeRepository.findByStatusAndPhoneAndCode(any(), any(), any()) } returns listOf()

            // when
            val exception = assertThrows<CustomException> {
                userService.signUp(req)
            }

            // then
            assertEquals(1, exception.code)
        }

        @Test
        fun success() {
            // given
            every { userRepository.existsByPhone(any()) } returns false
            every { phoneCodeRepository.findByStatusAndPhoneAndCode(any(), any(), any()) } returns
                    listOf(PhoneCodeEntityFixture.entity)
            every { phoneCodeRepository.saveAll(any<List<PhoneCodeEntity>>()) } returnsArgument 0
            every { schoolRepository.findByIdOrNull(req.schoolId) } returns SchoolFixture.school1
            every { userRepository.save(any()) } returnsArgument 0

            // when
            userService.signUp(req)

            // then
        }
    }
}