package com.ohayo.moyamoya.global.jwt

import com.ohayo.moyamoya.core.user.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    val user: UserEntity
) : UserDetails {
    override fun getAuthorities() = listOf(GrantedAuthority { user.userRole.name })
    override fun getPassword() = null
    override fun getUsername() = user.phone
    override fun isAccountNonExpired() = true // 계정이 만료되지 않았는지
    override fun isAccountNonLocked() = true // 계정이 잠기지 않았는지
    override fun isCredentialsNonExpired() = true // 비밀번호가 만료되지 않았는지
    override fun isEnabled() = true
}