package com.ohayo.moyamoya.core.user.profile

enum class Mbti {
    INFP,
    ENFP,
    INFJ,
    ENFJ,
    INTJ,
    ENTJ,
    INTP,
    ENTP,
    ISFP,
    ESFP,
    ISTP,
    ESTP,
    ISFJ,
    ESFJ,
    ISTJ,
    ESTJ;

    // MBTI 간의 호환 점수를 나타내는 2차원 배열
    companion object {
        private const val A = 15
        private const val B = 10
        private const val C = 0
        private const val D = -10
        private const val E = -15
        private val compatibilityChart: Array<Array<Int>> = arrayOf(
            arrayOf(B, B, B, A, B, A, B, B, E, E, E, E, E, E, E, E), // INFP
            arrayOf(B, B, A, B, A, B, B, B, E, E, E, E, E, E, E, E), // ENFP
            arrayOf(B, A, B, B, B, B, B, A, E, E, E, E, E, E, E, E), // INFJ
            arrayOf(A, B, B, B, B, B, B, B, A, E, E, E, E, E, E, E), // ENFJ
            arrayOf(B, A, B, B, B, B, B, A, C, C, C, C, D, D, D, D), // INTJ
            arrayOf(A, B, B, B, B, B, A, B, C, C, C, C, C, C, C, C), // ENTJ
            arrayOf(B, B, B, B, B, A, B, B, C, C, C, C, D, D, D, A), // INTP
            arrayOf(B, B, A, B, A, B, B, B, C, C, C, C, D, D, D, D), // ENTP
            arrayOf(E, E, E, A, C, C, C, C, D, D, D, D, C, A, C, A), // ISFP
            arrayOf(E, E, E, E, C, C, C, C, D, D, D, D, A, C, A, C), // ESFP
            arrayOf(E, E, E, E, C, C, C, C, D, D, D, D, C, A, C, A), // ISTP
            arrayOf(E, E, E, E, C, C, C, C, D, D, D, D, A, C, A, C), // ESTP
            arrayOf(E, E, E, E, D, C, D, D, C, A, C, A, B, B, B, B), // ISFJ
            arrayOf(E, E, E, E, D, C, D, D, A, C, A, C, B, B, B, B), // ESFJ
            arrayOf(E, E, E, E, D, C, D, D, C, A, C, A, B, B, B, B), // ISTJ
            arrayOf(E, E, E, E, D, C, A, D, A, C, A, C, B, B, B, B)  // ESTJ
        )
    }

    // 호환 점수를 반환하는 함수
    fun score(target: Mbti) = compatibilityChart[this.ordinal][target.ordinal]
} 