package com.ohayo.moyamoya.api.user.profile.core

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class MatchingResult(
    val score: Int,
    val male: UserProfileEntity,
    val female: UserProfileEntity,
)