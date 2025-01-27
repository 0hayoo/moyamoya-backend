package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.PersonalityEntity
import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class UserProfileRes(
    val userId: Int,
    val myInfo: MyInfoDto,
    val idealType: IdealTypeRes,
) {
    companion object {
        fun of(entity: UserProfileEntity, idealTypePersonalities: List<PersonalityEntity>) = UserProfileRes(
            userId = entity.id,
            myInfo = MyInfoDto.of(entity.myInfo),
            idealType = IdealTypeRes.of(entity.idealType, idealTypePersonalities)
        )
    }
}