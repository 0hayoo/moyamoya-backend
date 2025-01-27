package com.ohayo.moyamoya.api.user.profile

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.user.profile.value.PersonalityRes
import com.ohayo.moyamoya.api.user.profile.value.UpsertUserProfileReq
import com.ohayo.moyamoya.api.user.profile.value.UserProfileRes
import com.ohayo.moyamoya.core.user.profile.*
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UserProfileService(
    private val userProfileRepository: UserProfileRepository,
    private val sessionHolder: UserSessionHolder,
    private val idealTypeHasPersonalityRepository: IdealTypeHasPersonalityRepository,
    private val personalityRepository: PersonalityRepository
) {
    @Transactional(readOnly = true)
    fun getMyUserProfileInfo(): UserProfileRes {
        val userProfile = userProfileRepository.findByUserSafety(user = sessionHolder.current())
        val personalities = idealTypeHasPersonalityRepository.findByIdealType(userProfile.idealType)
        return UserProfileRes.of(userProfile, personalities.map { it.personality })
    }

    // todo personality 저장
    fun upsertUserProfile(req: UpsertUserProfileReq): VoidRes {
        val user = sessionHolder.current()
        var userProfile = userProfileRepository.findByUser(user)

        if (userProfile == null) {
            val entity = req.toEntity(user)
            userProfile = userProfileRepository.save(entity)
        } else {
            userProfile.update(req)
            userProfile = userProfileRepository.save(userProfile)

            idealTypeHasPersonalityRepository.removeByIdealType(userProfile.idealType)
        }

        idealTypeHasPersonalityRepository.saveAll(
            personalityRepository.findByIdIn(req.idealType.personalities).map {
                IdealTypeHasPersonalityEntity(
                    idealType = userProfile.idealType,
                    personality = it
                )
            }
        )

        return VoidRes()
    }

    fun getAvailablePersonalities() = personalityRepository.findAll().map(PersonalityRes::of)
}