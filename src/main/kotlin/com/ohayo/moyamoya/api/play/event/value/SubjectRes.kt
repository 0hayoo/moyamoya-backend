package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.question.SubjectEntity

data class SubjectRes(
    val id: Int,
    val title: String
) {
    companion object {
        fun of(entity: SubjectEntity) = SubjectRes(
            id = entity.id,
            title = entity.title
        )
    }
}
