package com.ohayo.moyamoya.core.user.profile

enum class AgeType {
    YOUNGER,
    SAME,
    OLDER,
    ANY;
    
    fun score(age: Int, targetAge: Int): Int {
        if (this == ANY) return 0
        if (isMatched(age, targetAge)) return 10
        return -10
    }

    private fun isMatched(age: Int, targetAge: Int) =
        age == targetAge && this == SAME || age > targetAge && this == YOUNGER || age < targetAge && this == OLDER
}