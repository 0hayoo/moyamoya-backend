package com.ohayo.moyamoya.api.play.value

import com.ohayo.moyamoya.api.user.profile.value.UserProfileRes
import com.ohayo.moyamoya.core.play.PlayEntity

data class Play(
    val male: UserProfileRes,
    val female: UserProfileRes,
) {
    companion object {
        fun of(entity: PlayEntity) = Play(
            male = UserProfileRes.of(entity.male),
            female = UserProfileRes.of(entity.female)
        )
    }
}