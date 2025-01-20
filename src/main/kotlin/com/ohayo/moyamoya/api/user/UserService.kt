package com.ohayo.moyamoya.api.user

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.ListObjectsV2Result
import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.user.value.*
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.core.phonecode.PhoneCodeEntity
import com.ohayo.moyamoya.core.phonecode.PhoneCodeRepository
import com.ohayo.moyamoya.core.school.SchoolRepository
import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.core.user.UserRepository
import com.ohayo.moyamoya.core.user.findByPhoneSafety
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import com.ohayo.moyamoya.infra.s3.S3Properties
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
    private val sessionHolder: UserSessionHolder,
    private val amazonS3: AmazonS3,
    private val s3Properties: S3Properties
) {
    fun sendCode(req: SendCodeReq): VoidRes {
        val code = smsClient.sendAuthorizationCode(req.phone)
        phoneCodeRepository.save(
            PhoneCodeEntity(
                phone = req.phone,
                code = code
            )
        )
        return VoidRes()
    }

    @Transactional(rollbackFor = [Exception::class])
    fun verifyCode(req: VerifyCodeReq): VerifyCodeRes {
        val codes = phoneCodeRepository.findByStatusAndPhoneAndCode(
            status = PhoneCodeEntity.Status.UNUSED,
            phone = req.phone,
            code = req.code
        )

        if (codes.isEmpty()) throw CustomException(HttpStatus.BAD_REQUEST, "인증 실패")

        codes.forEach { it.updateStatus() }
        phoneCodeRepository.saveAll(codes)

        val user = userRepository.findByPhone(req.phone)
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

    fun getAvailableProfiles(): List<String> {
        val urls = arrayListOf<String>()

        var result: ListObjectsV2Result
        val request = ListObjectsV2Request()
            .withBucketName(s3Properties.s3bucket)
            .withPrefix("profile-images/")
        
        do {
            result = amazonS3.listObjectsV2(request)
            for (objectSummary in result.objectSummaries) {
                if (objectSummary.size == 0L) continue
                
                val url = amazonS3.getUrl(s3Properties.s3bucket, objectSummary.key).toString()
                urls.add(url)
            }
            request.continuationToken = result.nextContinuationToken
        } while (result.isTruncated) // 페이지네이션 처리
        
        return urls
    }
}