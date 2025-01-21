package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SendCodeReq
import com.ohayo.moyamoya.api.user.value.SignUpReq
import com.ohayo.moyamoya.api.user.value.VerifyCodeReq
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserApi(
    private val userService: UserService
) {
    @PostMapping("send-code")
    fun sendCode(@RequestBody @Valid req: SendCodeReq) = userService.sendCode(req)

    @PostMapping("verify-code")
    fun verifyCode(@RequestBody @Valid req: VerifyCodeReq) = userService.verifyCode(req)

    @PostMapping("sign-up")
    fun signUp(@RequestBody @Valid req: SignUpReq) = userService.signUp(req)

    @GetMapping
    fun getMyInfo() = userService.getMyUserInfo()

    @PostMapping("refresh")
    fun refresh(@RequestBody @Valid req: RefreshReq) = userService.refresh(req)
    
    @GetMapping("available-profile-images")
    fun getAvailableProfiles() = userService.getAvailableProfileImages()
}