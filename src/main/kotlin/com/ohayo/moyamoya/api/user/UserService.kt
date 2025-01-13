package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SignUpReq
import com.ohayo.moyamoya.api.user.value.UserRes
import com.ohayo.moyamoya.api.user.value.VerifyCodeRes
import com.ohayo.moyamoya.core.*
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import com.ohayo.moyamoya.infra.sms.SmsClient
import com.ohayo.moyamoya.infra.token.JwtClient
import com.ohayo.moyamoya.infra.token.JwtPayloadKey
import com.ohayo.moyamoya.infra.token.Token
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val schoolRepository: SchoolRepository,
    private val jwtClient: JwtClient,
    private val smsClient: SmsClient,
    private val phoneCodeRepository: PhoneCodeRepository,
    private val sessionHolder: UserSessionHolder
) {
    fun sendCode(phone: String): VoidRes {
        val code = smsClient.sendAuthorizationCode(phone)
        phoneCodeRepository.save(
            PhoneCodeEntity(
                phone = phone,
                code = code
            )
        )
        return VoidRes()
    }

    @Transactional(rollbackFor = [Exception::class])
    fun verifyCode(phone: String, code: String): VerifyCodeRes {
        val codes = phoneCodeRepository.findByStatusAndPhoneAndCode(
            status = PhoneCodeEntity.Status.UNUSED,
            phone = phone,
            code = code
        )

        if (codes.isEmpty()) throw CustomException(HttpStatus.BAD_REQUEST, "인증 실패")

        codes.forEach { it.updateStatus() }
        phoneCodeRepository.saveAll(codes)

        val user = userRepository.findByPhone(phone)
        return if (user == null) {
            VerifyCodeRes(
                isNewUser = true,
                token = null
            )
        } else {
            VerifyCodeRes(
                isNewUser = false,
                token = jwtClient.generate(user)
            )
        }
    }

    fun signUp(req: SignUpReq): Token {
        if (userRepository.existsByPhone(req.phone)) throw CustomException(HttpStatus.BAD_REQUEST, "이미 가입된 계정")

        val codes = phoneCodeRepository.findByStatusAndPhoneAndCode(
            status = PhoneCodeEntity.Status.AUTHORIZED,
            phone = req.phone,
            code = req.code
        )

        if (codes.isEmpty()) throw CustomException(HttpStatus.BAD_REQUEST, "인증 실패")

        codes.forEach {
            it.updateStatus()
        }

        phoneCodeRepository.saveAll(codes)

        val school = schoolRepository.findByIdSafety(req.schoolId)
        val user = userRepository.save(
            UserEntity(
                phone = req.phone,
                school = school,
                schoolGrade = req.schoolGrade,
                schoolClass = req.schoolClass,
                name = req.name,
                gender = req.gender,
                profileImageUrl = req.profileImageUrl,
            )
        )

        return jwtClient.generate(user)
    }

    @Transactional(readOnly = true)
    fun getMyInfo(): UserRes = UserRes.of(sessionHolder.current())

    fun refresh(req: RefreshReq): Token {
        jwtClient.parseToken(req.refreshToken)

        val user = run {
            val phone = jwtClient.payload(JwtPayloadKey.PHONE, req.refreshToken)
            userRepository.findByPhoneSafety(phone)
        }

        return jwtClient.generate(user)
    }
}