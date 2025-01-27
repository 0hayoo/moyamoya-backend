package com.ohayo.moyamoya.api.test

import com.ohayo.moyamoya.api.user.UserProfileImageService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class TestUserProfileService : UserProfileImageService {
    override fun getAvailableProfileImages(): List<String> {
        return listOf("google.com")
    }
}