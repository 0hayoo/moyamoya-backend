package com.ohayo.moyamoya.global

import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.global.jwt.JwtUserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserSessionHolder {
    fun current(): UserEntity = (SecurityContextHolder.getContext().authentication.principal as? JwtUserDetails)?.user
        ?: throw CustomException(HttpStatus.NOT_FOUND, "Not found user")
}