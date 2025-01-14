package com.ohayo.moyamoya.global.jwt
import com.ohayo.moyamoya.core.UserRepository
import com.ohayo.moyamoya.core.findByPhoneSafety
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String) =
        JwtUserDetails(userRepository.findByPhoneSafety(username))
}