package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class UserProfileRes(
    val userId: Int,
    val myInfo: MyInfoDto,
    val idealType: IdealTypeDto,
) {
    companion object {
        fun of(entity: UserProfileEntity) = UserProfileRes(
            userId = entity.id,
            myInfo = MyInfoDto.of(entity.myInfo),
            idealType = IdealTypeDto.of(entity.idealType)
        )
    }
}