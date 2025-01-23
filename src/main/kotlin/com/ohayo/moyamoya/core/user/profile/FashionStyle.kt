package com.ohayo.moyamoya.core.user.profile

enum class FashionStyle {
    CASUAL,
    MINIMAL,
    STREET,
    VINTAGE,
    MODERN,
    CLASSIC,
    SPORTY, ;

    companion object {
        const val SPLITTER = " "

        fun of(name: String) = entries.firstOrNull { it.name == name }
        fun listOf(fashionStyle: String) =
            fashionStyle.split(FashionStyle.SPLITTER).mapNotNull(FashionStyle::of)
    }
}

fun List<FashionStyle>.toInternalForm(): String =
    joinToString(FashionStyle.SPLITTER) { it.name }