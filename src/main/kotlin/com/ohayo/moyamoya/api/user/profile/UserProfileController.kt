package com.ohayo.moyamoya.api.user.profile

import com.ohayo.moyamoya.api.user.profile.value.UpsertUserProfileReq
import com.ohayo.moyamoya.api.user.profile.value.UserProfileRes
import com.ohayo.moyamoya.core.user.profile.IdealTypeHasPersonalityRepository
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/profile")
class UserProfileController(
    private val userProfileService: UserProfileService,
) {
    @PostMapping
    fun upsert(
        @RequestBody @Valid req: UpsertUserProfileReq
    ) = userProfileService.upsertUserProfile(req)

    @GetMapping
    fun getMyProfileInfo() = userProfileService.getMyUserProfileInfo()
    
    @GetMapping("available-personalities")
    fun getAvailablePersonalities() = userProfileService.getAvailablePersonalities()
}