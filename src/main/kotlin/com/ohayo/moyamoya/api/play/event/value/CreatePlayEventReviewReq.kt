package com.ohayo.moyamoya.api.play.event.value

import com.ohayo.moyamoya.core.play.PlayEventEntity
import com.ohayo.moyamoya.core.play.PlayEventReviewEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.validation.constraints.NotNull

data class CreatePlayEventReviewReq(
    @field:NotNull
    val star: Int,

    @field:NotNull
    val content: String,
) {
    fun toEntity(
        user: UserEntity,
        playEvent: PlayEventEntity
    ) = PlayEventReviewEntity(
        star = star,
        content = content,
        user = user,
        playEvent = playEvent
    )
}