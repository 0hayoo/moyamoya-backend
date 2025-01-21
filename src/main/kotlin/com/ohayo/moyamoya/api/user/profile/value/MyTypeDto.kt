package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.*

data class MyTypeDto(
    val messageInterval: MessageInterval,
    val fashionStyle: FashionStyle,
    val hasGlasses: Boolean,
    val height: Int,
    val mbti: Mbti,
    val faceType: FaceType,
    val bodyType: BodyType,
    val hairStyle: HairStyleDto,
    val skinColor: SkinColor,
) {
    companion object {
        fun of(entity: MyTypeEntity) = MyTypeDto(
            messageInterval = entity.messageInterval,
            fashionStyle = entity.fashionStyle,
            hasGlasses = entity.hasGlasses,
            height = entity.height,
            mbti = entity.mbti,
            faceType = entity.faceType,
            bodyType = entity.bodyType,
            hairStyle = HairStyleDto.of(entity.hairStyle),
            skinColor = entity.skinColor,
        )
    }
    
    fun toEntity() = MyTypeEntity(
        messageInterval = messageInterval,
        fashionStyle = fashionStyle,
        hasGlasses = hasGlasses,
        height = height,
        mbti = mbti,
        faceType = faceType,
        bodyType = bodyType,
        hairStyle = hairStyle.toEntity(),
        skinColor = skinColor,
    )
}