package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SignUpReq
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserApi(
    private val userService: UserService
) {
    @PostMapping("send-code")
    fun sendCode(@RequestParam phone: String) = userService.sendCode(phone)
    
    @PostMapping("verify-code")
    fun verifyCode(@RequestParam phone: String, @RequestParam code: String) = userService.verifyCode(phone, code)

    @PostMapping("sign-up")
    fun signUp(@RequestBody @Valid req: SignUpReq) = userService.signUp(req)
    
    @GetMapping
    fun getMyInfo() = userService.getMyInfo()

    @PostMapping("refresh")
    fun refresh(@RequestBody @Valid req: RefreshReq) = userService.refresh(req)
}