package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.MyTypeDto
import jakarta.persistence.*

@Embeddable
class MyTypeEntity(
    @Column(nullable = false)
    var messageInterval: MessageInterval,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var fashionStyle: FashionStyle,

    @Column(nullable = false)
    var hasGlasses: Boolean,

    @Column(nullable = false)
    var height: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var mbti: Mbti,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var faceType: FaceType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var bodyType: BodyType,

    @Column(nullable = false)
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "length", column = Column(name = "myTypeHairStyleLength")),
        AttributeOverride(name = "isCurly", column = Column(name = "myTypeHairStyleIsCurly")),
        AttributeOverride(name = "hasPerm", column = Column(name = "myTypeHairStyleHasPerm")),
        AttributeOverride(name = "hasBang", column = Column(name = "myTypeHairStyleHasBang")),
    )
    val hairStyle: HairStyleEntity,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var skinColor: SkinColor
)

fun MyTypeEntity.update(dto: MyTypeDto) {
    messageInterval = dto.messageInterval
    fashionStyle = dto.fashionStyle
    hasGlasses = dto.hasGlasses
    height = dto.height
    mbti = dto.mbti
    faceType = dto.faceType
    bodyType = dto.bodyType
    hairStyle.update(dto.hairStyle)
    skinColor = dto.skinColor
}