package com.ohayo.moyamoya.api.play.core

import com.ohayo.moyamoya.core.user.profile.UserProfileEntity

data class MatchingGraphEdge(
    var score: Int,
    var male: UserProfileEntity,
    var female: UserProfileEntity,
    var visited: Boolean = false
)