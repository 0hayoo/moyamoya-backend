package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SignUpRequest
import com.ohayo.moyamoya.core.*
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.global.CustomException
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
) {
    fun signUp(req: SignUpRequest): Token {
        if (userRepository.existsByTel(req.tel)) throw CustomException(HttpStatus.BAD_REQUEST, "이미 가입된 계정")
        
        val school = schoolRepository.findByIdSafety(req.schoolId)

        userRepository.save(
            UserEntity(
                tel = req.tel,
                school = school,
                schoolGrade = req.schoolGrade,
                schoolClass = req.schoolClass,
                name = req.name,
                gender = req.gender,
                password = req.password,
                profileImageUrl = req.profileImageUrl,
            )
        )

        // TODO: JWT
        return Token(
            "", ""
        )
    }

    fun exists(tel: String) = userRepository.existsByTel(tel)



    fun refresh(req: RefreshReq): Token {
        jwtClient.parseToken(req.refreshToken)

        val user = run {
            val tel = jwtClient.payload(JwtPayloadKey.TEL, req.refreshToken)
            userRepository.findByTelSafety(tel)
        }

        return jwtClient.generate(user)
    }
}