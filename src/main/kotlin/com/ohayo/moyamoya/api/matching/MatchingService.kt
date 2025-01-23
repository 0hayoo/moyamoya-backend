package com.ohayo.moyamoya.api.matching

import com.ohayo.moyamoya.api.user.profile.core.MatchingHelper
import com.ohayo.moyamoya.api.user.profile.core.MatchingResult
import com.ohayo.moyamoya.core.user.profile.UserProfileRepository
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.stereotype.Service

@Service
class MatchingService(
    private val userProfileRepository: UserProfileRepository,
    private val sessionHolder: UserSessionHolder
) {
    fun getTodayMatching(): List<MatchingResult> {
        val user = sessionHolder.current()
        val users = userProfileRepository.findBySchoolId(user.school.id)

        return MatchingHelper.matchUsers(users)
    }
}