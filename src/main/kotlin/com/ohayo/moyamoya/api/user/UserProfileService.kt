package com.ohayo.moyamoya.api.user

interface UserProfileService {
    fun getAvailableProfileImages(): List<String>
}