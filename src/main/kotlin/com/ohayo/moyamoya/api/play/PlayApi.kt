package com.ohayo.moyamoya.api.play

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/plays")
@RestController
class PlayApi(
    private val playService: PlayService
) {
    @GetMapping("today")
    fun getTodayPlay() = playService.getTodayPlay()
}