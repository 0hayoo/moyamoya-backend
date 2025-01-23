package com.ohayo.moyamoya.api.matching

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/matching")
class MatchingApi(
    private val matchingService: MatchingService
) {
    @GetMapping("today")
    fun getTodayMatching() = matchingService.getTodayMatching()
}