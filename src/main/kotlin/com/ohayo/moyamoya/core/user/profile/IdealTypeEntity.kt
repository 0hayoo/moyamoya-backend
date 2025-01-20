package com.ohayo.moyamoya.core.user.profile

import jakarta.persistence.*

@Embeddable
class IdealTypeEntity(
    @Column(nullable = false)
    val messageInterval: MessageInterval,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val fashionStyle: FashionStyle,

    @Column(nullable = false)
    val hasGlasses: Boolean,

    @Column(nullable = false)
    val heightLevel: HeightLevel,

    // TODO: 성격
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    val mbti: Mbti,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val faceType: FaceType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val bodyType: BodyType,

    @Column(nullable = false)
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "length", column = Column(name = "idealTypeHairStyleLength")),
        AttributeOverride(name = "isCurly", column = Column(name = "idealTypeHairStyleIsCurly")),
        AttributeOverride(name = "hasPerm", column = Column(name = "idealTypeHairStyleHasPerm")),
        AttributeOverride(name = "hasBang", column = Column(name = "idealTypeHairStyleHasBang")),
    )
    val hairStyle: HairStyle,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val skinColor: SkinColor,
)