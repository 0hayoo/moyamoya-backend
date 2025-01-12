package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.user.value.RefreshReq
import com.ohayo.moyamoya.api.user.value.SignUpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserApi(
    private val userService: UserService
) {
    @PostMapping("sign-up")
    fun signUp(req: SignUpRequest) = userService.signUp(req)

    @GetMapping("exists")
    fun exists(@RequestParam phone: String) = userService.exists(phone)
    
    @PostMapping("refresh")
    fun refresh(@RequestBody req: RefreshReq) = userService.refresh(req)
    
    @PostMapping("authorization-code")
    fun sendAuthorizationCode(@RequestParam phone: String) = userService.sendAuthorizationCode(phone)
    
    @PostMapping("authorize-code")
    fun authorizeCode(@RequestParam phone: String, @RequestParam code: String) = userService.authorizeCode(phone, code)
}