package com.ohayo.moyamoya.global.jwt

import com.ohayo.moyamoya.core.user.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    val user: UserEntity
) : UserDetails {
    override fun getAuthorities() = listOf(GrantedAuthority { user.userRole.role })
    override fun getPassword() = null
    override fun getUsername() = user.phone
}