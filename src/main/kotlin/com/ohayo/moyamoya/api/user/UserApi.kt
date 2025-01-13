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
    @GetMapping("exists")
    fun exists(@RequestParam phone: String) = userService.exists(phone)
    
    @PostMapping("authorization-code")
    fun sendAuthorizationCode(@RequestParam phone: String) = userService.sendAuthorizationCode(phone)
    
    @PostMapping("authorize-code")
    fun authorizeCode(@RequestParam phone: String, @RequestParam code: String) = userService.authorizeCode(phone, code)

    @PostMapping("sign-up")
    fun signUp(@RequestBody @Valid req: SignUpReq) = userService.signUp(req)
    
    @GetMapping
    fun getMyInfo() = userService.getMyInfo()

    @PostMapping("refresh")
    fun refresh(@RequestBody @Valid req: RefreshReq) = userService.refresh(req)
}