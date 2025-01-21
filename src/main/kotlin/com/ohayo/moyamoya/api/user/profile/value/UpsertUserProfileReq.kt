package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.core.user.profile.*

data class UpsertUserProfileReq(
    val myType: MyTypeDto,
    val idealType: IdealTypeDto,
) {
    fun toEntity(user: UserEntity) = UserProfileEntity(
        user = user,
        myType = myType.toEntity(),
        idealType = idealType.toEntity()
    )
}