package com.ohayo.moyamoya.api.user.profile.core

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class MatchResult(
    val male: UserProfileEntity,
    val female: UserProfileEntity,
)