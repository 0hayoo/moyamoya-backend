package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class UserProfileRes(
    val userId: Int,
    val myType: MyTypeDto,
    val idealType: IdealTypeDto,
) {
    companion object {
        fun of(entity: UserProfileEntity) = UserProfileRes(
            userId = entity.id,
            myType = MyTypeDto.of(entity.myType),
            idealType = IdealTypeDto.of(entity.idealType)
        )
    }
}