package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SignUpRequest
import com.ohayo.moyamoya.core.*
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.infra.sms.SmsClient
import com.ohayo.moyamoya.infra.token.JwtClient
import com.ohayo.moyamoya.infra.token.JwtPayloadKey
import com.ohayo.moyamoya.infra.token.Token
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val schoolRepository: SchoolRepository,
    private val jwtClient: JwtClient,
    private val smsClient: SmsClient,
    private val phoneCodeRepository: PhoneCodeRepository
) {
    fun signUp(req: SignUpRequest): Token {
        if (userRepository.existsByPhone(req.phone)) throw CustomException(HttpStatus.BAD_REQUEST, "이미 가입된 계정")
        
        val school = schoolRepository.findByIdSafety(req.schoolId)

        val user = userRepository.save(
            UserEntity(
                phone = req.phone,
                school = school,
                schoolGrade = req.schoolGrade,
                schoolClass = req.schoolClass,
                name = req.name,
                gender = req.gender,
                password = req.password,
                profileImageUrl = req.profileImageUrl,
            )
        )
        
        return jwtClient.generate(user)
    }
    
    fun sendAuthorizationCode(phone: String) {
        val code = smsClient.sendAuthorizationCode(phone)
        phoneCodeRepository.save(
            PhoneCodeEntity(
                phone = phone,
                code = code
            )
        )
    }
    
    fun authorizeCode(phone: String, code: String) {
        val codes = phoneCodeRepository.findByIsActive(phone = phone, code = code)
        if (codes.isEmpty()) {
            throw CustomException(HttpStatus.BAD_REQUEST, "인증 실패")
        }
        
        val disabledCodes = codes.map {
            it.disable()
            it
        }
        
        phoneCodeRepository.saveAll(disabledCodes)
    }

    fun exists(phone: String) = userRepository.existsByPhone(phone)
    
    fun refresh(req: RefreshReq): Token {
        jwtClient.parseToken(req.refreshToken)

        val user = run {
            val phone = jwtClient.payload(JwtPayloadKey.PHONE, req.refreshToken)
            userRepository.findByPhoneSafety(phone)
        }

        return jwtClient.generate(user)
    }
}