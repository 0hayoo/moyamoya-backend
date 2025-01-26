package com.ohayo.moyamoya.api.play.value

import com.ohayo.moyamoya.api.user.profile.value.UserProfileRes
import com.ohayo.moyamoya.core.play.PlayEntity

data class PlayRes(
    val id: Int,
    val male: UserProfileRes,
    val female: UserProfileRes,
    val score: Int
) {
    companion object {
        fun of(entity: PlayEntity) = PlayRes(
            id = entity.id,
            male = UserProfileRes.of(entity.male),
            female = UserProfileRes.of(entity.female),
            score = entity.score
        )
    }
}