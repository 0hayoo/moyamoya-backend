package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.*

data class IdealTypeRes(
    val messageInterval: MessageInterval,
    val fashionStyle: List<FashionStyle>,
    val hasGlasses: Boolean,
    val heightLevel: HeightLevel,
    val ageType: AgeType,
    val personalities: List<PersonalityRes>,
    val faceType: FaceType,
    val bodyType: BodyType,
    val hairStyle: HairStyleDto,
    val skinColor: SkinColor,
) {
    companion object {
        fun of(entity: IdealTypeEntity, personalities: List<PersonalityEntity>) = IdealTypeRes(
            messageInterval = entity.messageInterval,
            fashionStyle = FashionStyle.listOf(entity.fashionStyle),
            hasGlasses = entity.hasGlasses,
            heightLevel = entity.heightLevel,
            ageType = entity.ageType,
            personalities = personalities.map(PersonalityRes::of),
            faceType = entity.faceType,
            bodyType = entity.bodyType,
            hairStyle = HairStyleDto.of(entity.hairStyle),
            skinColor = entity.skinColor,
        )
    }
}