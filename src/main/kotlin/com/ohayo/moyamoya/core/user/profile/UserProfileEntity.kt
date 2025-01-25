package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.UpsertUserProfileReq
import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user_profile")
class UserProfileEntity(
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne
    val user: UserEntity,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "messageInterval", column = Column(name = "myInfoMessageInterval")),
        AttributeOverride(name = "fashionStyle", column = Column(name = "myInfoFashionStyle")),
        AttributeOverride(name = "hasGlasses", column = Column(name = "myInfoHasGlasses")),
        AttributeOverride(name = "height", column = Column(name = "myInfoHeight")),
        AttributeOverride(name = "mbti", column = Column(name = "myInfoMbti")),
        AttributeOverride(name = "faceType", column = Column(name = "myInfoFaceType")),
        AttributeOverride(name = "bodyType", column = Column(name = "myInfoBodyType")),
        AttributeOverride(name = "skinColor", column = Column(name = "myInfoSkinColor")),
    )
    val myInfo: MyInfoEntity,

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
    myInfo.update(req.myInfo)
    idealType.update(req.idealType)
}