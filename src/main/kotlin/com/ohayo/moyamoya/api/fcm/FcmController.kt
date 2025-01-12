package com.ohayo.moyamoya.api.fcm

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("fcm")
class FcmController(
    private val fcmService: FcmService
) {
    @PostMapping
    fun register(@RequestBody req: RegisterFcmTokenReq) = fcmService.register(req)

    @PostMapping("test/me")
    fun testSend(
        @RequestParam title: String,
        @RequestParam body: String
    ) = fcmService.sendMessageToMe(title, body)
}