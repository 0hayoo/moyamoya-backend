package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.PersonalityEntity

data class PersonalityRes(
    val id: Int,
    val content: String
) {
    companion object {
        fun of(entity: PersonalityEntity) = PersonalityRes(
            id = entity.id,
            content = entity.content
        )
    }
}