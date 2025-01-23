package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.MyInfoDto
import jakarta.persistence.*

@Embeddable
class MyInfoEntity(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var messageInterval: MessageInterval,

    @Column(nullable = false, columnDefinition = "TEXT")
    var fashionStyle: String,

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
        AttributeOverride(name = "length", column = Column(name = "myInfoHairStyleLength")),
        AttributeOverride(name = "isCurly", column = Column(name = "myInfoHairStyleIsCurly")),
        AttributeOverride(name = "hasPerm", column = Column(name = "myInfoHairStyleHasPerm")),
        AttributeOverride(name = "hasBang", column = Column(name = "myInfoHairStyleHasBang")),
    )
    val hairStyle: HairStyleEntity,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var skinColor: SkinColor
)

fun MyInfoEntity.update(dto: MyInfoDto) {
    messageInterval = dto.messageInterval
    fashionStyle = dto.fashionStyle.toInternalForm()
    hasGlasses = dto.hasGlasses
    height = dto.height
    mbti = dto.mbti
    faceType = dto.faceType
    bodyType = dto.bodyType
    hairStyle.update(dto.hairStyle)
    skinColor = dto.skinColor
}