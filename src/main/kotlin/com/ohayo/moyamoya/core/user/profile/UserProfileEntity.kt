package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.UpsertUserProfileReq
import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user_profile")
class UserProfileEntity(
    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne
    val user: UserEntity,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "messageInterval", column = Column(name = "myTypeMessageInterval")),
        AttributeOverride(name = "fashionStyle", column = Column(name = "myTypeFashionStyle")),
        AttributeOverride(name = "hasGlasses", column = Column(name = "myTypeHasGlasses")),
        AttributeOverride(name = "height", column = Column(name = "myTypeHeight")),
        AttributeOverride(name = "mbti", column = Column(name = "myTypeMbti")),
        AttributeOverride(name = "faceType", column = Column(name = "myTypeFaceType")),
        AttributeOverride(name = "bodyType", column = Column(name = "myTypeBodyType")),
        AttributeOverride(name = "skinColor", column = Column(name = "myTypeSkinColor")),
    )
    val myType: MyTypeEntity,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "messageInterval", column = Column(name = "idealTypeMessageInterval")),
        AttributeOverride(name = "fashionStyle", column = Column(name = "idealTypeFashionStyle")),
        AttributeOverride(name = "hasGlasses", column = Column(name = "idealTypeHasGlasses")),
        AttributeOverride(name = "heightLevel", column = Column(name = "idealTypeHeightLevel")),
        AttributeOverride(name = "personality", column = Column(name = "idealTypePersonality")),
        AttributeOverride(name = "faceType", column = Column(name = "idealTypeFaceType")),
        AttributeOverride(name = "bodyType", column = Column(name = "idealTypeBodyType")),
        AttributeOverride(name = "skinColor", column = Column(name = "idealTypeSkinColor")),
    )
    val idealType: IdealTypeEntity
) : BaseEntity()

fun UserProfileEntity.update(req: UpsertUserProfileReq) {
    myType.update(req.myType)
    idealType.update(req.idealType)
}