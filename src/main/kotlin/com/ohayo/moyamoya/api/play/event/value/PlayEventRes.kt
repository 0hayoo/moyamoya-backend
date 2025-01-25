package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.play.PlayEventEntity
import java.time.LocalDateTime

data class PlayEventRes(
    val id: Int,
    val eventTime: LocalDateTime,
    val subject: SubjectRes,
) {
    companion object {
        fun of(entity: PlayEventEntity) = PlayEventRes(
            id = entity.id,
            eventTime = entity.eventTime,
            subject = SubjectRes.of(entity.subject)
        )
    }
}