package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.*

data class MyInfoDto(
    val messageInterval: MessageInterval,
    val fashionStyle: List<FashionStyle>,
    val hasGlasses: Boolean,
    val height: Int,
    val mbti: MBTI,
    val faceType: FaceType,
    val bodyType: BodyType,
    val hairStyle: HairStyleDto,
    val skinColor: SkinColor,
) {
    companion object {
        fun of(entity: MyInfoEntity) = MyInfoDto(
            messageInterval = entity.messageInterval,
            fashionStyle = FashionStyle.listOf(entity.fashionStyle),
            hasGlasses = entity.hasGlasses,
            height = entity.height,
            mbti = entity.mbti,
            faceType = entity.faceType,
            bodyType = entity.bodyType,
            hairStyle = HairStyleDto.of(entity.hairStyle),
            skinColor = entity.skinColor,
        )
    }
    
    fun toEntity() = MyInfoEntity(
        messageInterval = messageInterval,
        fashionStyle = fashionStyle.toInternalForm(),
        hasGlasses = hasGlasses,
        height = height,
        mbti = mbti,
        faceType = faceType,
        bodyType = bodyType,
        hairStyle = hairStyle.toEntity(),
        skinColor = skinColor,
    )
}