package com.ohayo.moyamoya.api.play.core

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class MatchingResult(
    val score: Int,
    val male: UserProfileEntity,
    val female: UserProfileEntity,
)