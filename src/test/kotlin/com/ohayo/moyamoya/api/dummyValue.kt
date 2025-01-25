package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.school.SchoolEntity
import com.ohayo.moyamoya.core.school.SchoolType
import com.ohayo.moyamoya.core.user.UserEntity
import java.time.LocalDate

val testSchool1 = SchoolEntity(
    name = "testName",
    type = SchoolType.HIGH,
    cityName = "testCityName",
    postalCode = "12345",
    address = "testAddress",
    addressDetail = "testAddressDetail",
    phone = "testPhone",
    website = "test.com",
    foundedAt = LocalDate.now(),
    anniversary = LocalDate.now(),
    schoolCode = "testSchoolCode",
    officeCode = "testOfficeCode",
)

val testUser1 = UserEntity(
    phone = "testPhoneUser1",
    school = testSchool1,
    schoolGrade = 1,
    schoolClass = 1,
    name = "testName1",
    gender = Gender.MALE,
    profileImageUrl = "profileImageUrl",
)


val testUser2 = UserEntity(
    phone = "testPhoneUser2",
    school = testSchool1,
    schoolGrade = 2,
    schoolClass = 2,
    name = "testName2",
    gender = Gender.FEMALE,
    profileImageUrl = "profileImageUrl",
)