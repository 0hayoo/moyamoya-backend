package com.ohayo.moyamoya.api.user.profile.core

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class MatchGraphEdge(
    var score: Int,
    var male: UserProfileEntity,
    var female: UserProfileEntity,
    var visited: Boolean = false
)