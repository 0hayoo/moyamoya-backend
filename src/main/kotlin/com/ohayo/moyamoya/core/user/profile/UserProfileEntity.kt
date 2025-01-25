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
        AttributeOverride(name = "messageInterval", column = Column(name = "myInfoMessageInterval", nullable = false)),
        AttributeOverride(name = "fashionStyle", column = Column(name = "myInfoFashionStyle", nullable = false)),
        AttributeOverride(name = "hasGlasses", column = Column(name = "myInfoHasGlasses", nullable = false)),
        AttributeOverride(name = "height", column = Column(name = "myInfoHeight", nullable = false)),
        AttributeOverride(name = "mbti", column = Column(name = "myInfoMbti", nullable = false)),
        AttributeOverride(name = "faceType", column = Column(name = "myInfoFaceType", nullable = false)),
        AttributeOverride(name = "bodyType", column = Column(name = "myInfoBodyType", nullable = false)),
        AttributeOverride(name = "skinColor", column = Column(name = "myInfoSkinColor", nullable = false)),
    )
    val myInfo: MyInfoEntity,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "messageInterval", column = Column(name = "idealTypeMessageInterval", nullable = false)),
        AttributeOverride(name = "fashionStyle", column = Column(name = "idealTypeFashionStyle", nullable = false)),
        AttributeOverride(name = "hasGlasses", column = Column(name = "idealTypeHasGlasses", nullable = false)),
        AttributeOverride(name = "heightLevel", column = Column(name = "idealTypeHeightLevel", nullable = false)),
        AttributeOverride(name = "ageType", column = Column(name = "idealTypeAgeType", nullable = false)),
        AttributeOverride(name = "personality", column = Column(name = "idealTypePersonality", nullable = false)),
        AttributeOverride(name = "faceType", column = Column(name = "idealTypeFaceType", nullable = false)),
        AttributeOverride(name = "bodyType", column = Column(name = "idealTypeBodyType", nullable = false)),
        AttributeOverride(name = "skinColor", column = Column(name = "idealTypeSkinColor", nullable = false)),
    )
    val idealType: IdealTypeEntity
) : BaseEntity()

fun UserProfileEntity.update(req: UpsertUserProfileReq) {
    myInfo.update(req.myInfo)
    idealType.update(req.idealType)
}