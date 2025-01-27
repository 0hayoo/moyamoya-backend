package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.core.user.profile.*
import jakarta.validation.constraints.NotNull

data class UpsertUserProfileReq(
    @field:NotNull
    val myInfo: MyInfoDto,
    @field:NotNull
    val idealType: IdealTypeReq,
) {
    fun toEntity(user: UserEntity) = UserProfileEntity(
        user = user,
        myInfo = myInfo.toEntity(),
        idealType = idealType.toEntity()
    )
}