package com.ohayo.moyamoya.core.user.profile

enum class AgeType {
    YOUNGER,
    SAME,
    OLDER;

    fun isMatched(age: Int, targetAge: Int) =
        age == targetAge && this == SAME || age > targetAge && this == YOUNGER || age < targetAge && this == OLDER
}