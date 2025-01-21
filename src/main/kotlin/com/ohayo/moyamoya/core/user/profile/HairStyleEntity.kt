package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.HairStyleDto
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class HairStyleEntity(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var length: HairLength,

    @Column(nullable = false)
    var isCurly: Boolean,

    @Column(nullable = false)
    var hasPerm: Boolean?, // null for males, true/false for females

    @Column(nullable = false)
    var hasBang: Boolean?, // null for males, true/false for females
)

fun HairStyleEntity.update(dto: HairStyleDto) {
    length = dto.length
    isCurly = dto.isCurly
    hasPerm = dto.hasPerm
    hasBang = dto.hasBang
}