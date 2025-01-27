package com.ohayo.moyamoya.api.user.profile.value

import com.ohayo.moyamoya.core.user.profile.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class IdealTypeReq(
    @field:NotNull
    val messageInterval: MessageInterval,
    @field:NotNull
    val fashionStyle: List<FashionStyle>,
    @field:NotNull
    val hasGlasses: Boolean,
    @field:NotNull
    val heightLevel: HeightLevel,
    @field:NotNull
    val ageType: AgeType,
    @field:NotNull
    @Size(min = 5, max = 5) // 5개 고정
    val personalities: List<Int>,
    @field:NotNull
    val faceType: FaceType,
    @field:NotNull
    val bodyType: BodyType,
    @field:NotNull
    val hairStyle: HairStyleDto,
    @field:NotNull
    val skinColor: SkinColor,
) {
    fun toEntity() = IdealTypeEntity(
        messageInterval = messageInterval,
        fashionStyle = fashionStyle.toInternalForm(),
        hasGlasses = hasGlasses,
        heightLevel = heightLevel,
        ageType = ageType,
        faceType = faceType,
        bodyType = bodyType,
        hairStyle = hairStyle.toEntity(),
        skinColor = skinColor,
    )
}