package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.IdealTypeReq
import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_ideal_type")
class IdealTypeEntity(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var messageInterval: MessageInterval,

    @Column(nullable = false, columnDefinition = "TEXT")
    var fashionStyle: String,

    @Column(nullable = false)
    var hasGlasses: Boolean,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var heightLevel: HeightLevel,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var ageType: AgeType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var faceType: FaceType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var bodyType: BodyType,

    @Column(nullable = false)
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "length", column = Column(name = "idealTypeHairStyleLength")),
        AttributeOverride(name = "isCurly", column = Column(name = "idealTypeHairStyleIsCurly")),
        AttributeOverride(name = "hasPerm", column = Column(name = "idealTypeHairStyleHasPerm")),
        AttributeOverride(name = "hasBang", column = Column(name = "idealTypeHairStyleHasBang")),
    )
    val hairStyle: HairStyleEntity,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var skinColor: SkinColor,
) : BaseEntity() {
    fun update(dto: IdealTypeReq) {
        messageInterval = dto.messageInterval
        fashionStyle = dto.fashionStyle.toInternalForm()
        hasGlasses = dto.hasGlasses
        heightLevel = dto.heightLevel
        // todo
//        personalities = dto.personalities
        faceType = dto.faceType
        bodyType = dto.bodyType
        hairStyle.update(dto.hairStyle)
        skinColor = dto.skinColor
    }
}