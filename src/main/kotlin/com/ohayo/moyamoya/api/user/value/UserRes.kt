package com.ohayo.moyamoya.api.user.value

import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.school.SchoolEntity
import com.ohayo.moyamoya.core.user.UserEntity

data class UserRes(
    val id: Int,
    val phone: String,
    val school: SchoolEntity,
    val schoolGrade: Int,
    val schoolClass: Int,
    val name: String,
    val gender: Gender,
    val profileImageUrl: String,
) {
    companion object {
        fun of(entity: UserEntity) = UserRes(
            id = entity.id,
            phone = entity.phone,
            school = entity.school,
            schoolGrade = entity.schoolGrade,
            schoolClass = entity.schoolClass,
            name = entity.name,
            gender = entity.gender,
            profileImageUrl = entity.profileImageUrl,
        )
    }
}
