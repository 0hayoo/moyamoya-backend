package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.api.user.profile.value.UpsertUserProfileReq
import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "tbl_user_profile",
    uniqueConstraints = [
        UniqueConstraint(
            name = "UniqueUserAndMyInfoAndIdealType",
            columnNames = ["user_id", "my_info_id", "ideal_type_id"]
        )
    ]
)
class UserProfileEntity(
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne
    val user: UserEntity,

    @JoinColumn(name = "my_info_id", nullable = false)
    @OneToOne
    val myInfo: MyInfoEntity,

    @JoinColumn(name = "ideal_type_id", nullable = false)
    @OneToOne
    val idealType: IdealTypeEntity
) : BaseEntity() {
    fun update(req: UpsertUserProfileReq) {
        myInfo.update(req.myInfo)
        idealType.update(req.idealType)
    }
}
