package com.ohayo.moyamoya.api.user

import com.ohayo.moyamoya.api.school.SchoolFixture
import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.user.UserEntity

object UserFixture {
    val user1 = UserEntity(
        phone = "testPhoneUser1",
        school = SchoolFixture.school1,
        schoolGrade = 1,
        schoolClass = 1,
        name = "testName1",
        gender = Gender.MALE,
        profileImageUrl = "profileImageUrl",
    )

    val user2 = UserEntity(
        phone = "testPhoneUser2",
        school = SchoolFixture.school1,
        schoolGrade = 2,
        schoolClass = 2,
        name = "testName2",
        gender = Gender.FEMALE,
        profileImageUrl = "profileImageUrl",
    )
}