package com.ohayo.moyamoya.api.play.value

import com.ohayo.moyamoya.api.user.value.UserRes
import com.ohayo.moyamoya.core.play.PlayEntity

data class PlayRes(
    val id: Int,
    val male: UserRes,
    val female: UserRes,
    val maleScore: Int,
    val femaleScore: Int,
    val score: Int
) {
    companion object {
        fun of(entity: PlayEntity) = PlayRes(
            id = entity.id,
            male = UserRes.of(entity.male.user),
            female = UserRes.of(entity.female.user),
            maleScore = entity.maleScore,
            femaleScore = entity.femaleScore,
            score = entity.score
        )
    }
}