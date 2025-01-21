package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.HairLength
import com.ohayo.moyamoya.core.user.profile.HairStyleEntity

data class HairStyleDto(
    val length: HairLength,
    val isCurly: Boolean,
    val hasPerm: Boolean?,
    val hasBang: Boolean?,
) {
    companion object {
        fun of(entity: HairStyleEntity) = HairStyleDto(
            length = entity.length,
            isCurly = entity.isCurly,
            hasPerm = entity.hasPerm,
            hasBang = entity.hasBang,
        )
    }

    fun toEntity() = HairStyleEntity(
        length = length,
        isCurly = isCurly,
        hasPerm = hasPerm,
        hasBang = hasBang,
    )
}
