package com.ohayo.moyamoya.core.user.profile

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class HairStyle(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val length: Length,

    @Column(nullable = false)
    val isCurly: Boolean,

    @Column(nullable = false)
    val hasPerm: Boolean?, // null for males, true/false for females

    @Column(nullable = false)
    val hasBang: Boolean?, // null for males, true/false for females
) {
    enum class Length {
        SHORT_CUT,
        SHORT_HAIR,
        MEDIUM,
        LONG
    }
}

/**
 *
 *     // 공통
 *     Straight,
 *     Curly,
 *
 *     // 남자
 *     ShortCut, // 숏컷
 *     Bob, // 단발
 *     MediumBob, // 중단발
 *     Long,
 */