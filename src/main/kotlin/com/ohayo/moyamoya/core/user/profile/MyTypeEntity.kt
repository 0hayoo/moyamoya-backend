package com.ohayo.moyamoya.core.user.profile

import jakarta.persistence.*

@Embeddable
class MyTypeEntity(
    @Column(nullable = false)
    val messageInterval: MessageInterval,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val fashionStyle: FashionStyle,
    
    @Column(nullable = false)
    val hasGlasses: Boolean,
    
    @Column(nullable = false)
    val height: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val mbti: Mbti,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val faceType: FaceType,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val bodyType: BodyType,
    
    @Column(nullable = false)
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "length", column = Column(name = "myTypeHairStyleLength")),
        AttributeOverride(name = "isCurly", column = Column(name = "myTypeHairStyleIsCurly")),
        AttributeOverride(name = "hasPerm", column = Column(name = "myTypeHairStyleHasPerm")),
        AttributeOverride(name = "hasBang", column = Column(name = "myTypeHairStyleHasBang")),
    )
    val hairStyle: HairStyle,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val skinColor: SkinColor,
)