package com.ohayo.moyamoya.api.user

interface UserProfileImageService {
    fun getAvailableProfileImages(): List<String>
}