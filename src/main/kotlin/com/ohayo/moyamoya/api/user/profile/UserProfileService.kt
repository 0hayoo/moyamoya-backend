package com.ohayo.moyamoya.api.user.profile

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.user.profile.value.UpsertUserProfileReq
import com.ohayo.moyamoya.core.user.profile.HeightLevel
import com.ohayo.moyamoya.core.user.profile.UserProfileRepository
import com.ohayo.moyamoya.core.user.profile.update
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UserProfileService(
    private val userProfileRepository: UserProfileRepository,
    private val sessionHolder: UserSessionHolder
) {
    @Transactional(readOnly = true)
    fun getMyUserProfileInfo() = userProfileRepository.findByUser(user = sessionHolder.current())
        ?: throw CustomException(HttpStatus.NOT_FOUND, "유저 프로필을 찾을 수 없습니다")
    
    fun upsertUserProfile(req: UpsertUserProfileReq): VoidRes {
        val user = sessionHolder.current()
        val userProfile = userProfileRepository.findByUser(user)

        if (userProfile == null) {
            val entity = req.toEntity(user)
            userProfileRepository.save(entity)
        } else {
            userProfile.update(req)
            userProfileRepository.save(userProfile)
        }

        return VoidRes()
    }
}