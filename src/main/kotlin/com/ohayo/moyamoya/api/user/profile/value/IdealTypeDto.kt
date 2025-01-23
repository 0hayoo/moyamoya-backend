package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.*

data class IdealTypeDto(
    val messageInterval: MessageInterval,
    val fashionStyle: List<FashionStyle>,
    val hasGlasses: Boolean,
    val heightLevel: HeightLevel,
    val ageType: AgeType,
    val personality: String,
    val faceType: FaceType,
    val bodyType: BodyType,
    val hairStyle: HairStyleDto,
    val skinColor: SkinColor,
) {
    companion object {
        fun of(entity: IdealTypeEntity) = IdealTypeDto(
            messageInterval = entity.messageInterval,
            fashionStyle = entity.fashionStyles,
            hasGlasses = entity.hasGlasses,
            heightLevel = entity.heightLevel,
            ageType = entity.ageType,
            personality = entity.personality,
            faceType = entity.faceType,
            bodyType = entity.bodyType,
            hairStyle = HairStyleDto.of(entity.hairStyle),
            skinColor = entity.skinColor,
        )
    }

    fun toEntity() = IdealTypeEntity(
        messageInterval = messageInterval,
        fashionStyle = fashionStyle.toInternalForm(),
        hasGlasses = hasGlasses,
        heightLevel = heightLevel,
        ageType = ageType,
        personality = personality,
        faceType = faceType,
        bodyType = bodyType,
        hairStyle = hairStyle.toEntity(),
        skinColor = skinColor,
    )
}