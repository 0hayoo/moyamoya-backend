package com.ohayo.moyamoya.common

object ListUtil {
    fun <T> transpose(data: List<List<T>>): List<List<T>> {
        return if (data.isEmpty()) emptyList() else data.first().indices.map { colIndex ->
            data.mapNotNull { row -> row.getOrElse(colIndex) { null } }
        }
    }
}