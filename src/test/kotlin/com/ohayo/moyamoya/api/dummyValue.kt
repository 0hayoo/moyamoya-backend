package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.school.SchoolEntity
import com.ohayo.moyamoya.core.school.SchoolType
import com.ohayo.moyamoya.core.user.UserEntity
import java.time.LocalDate

val dummySchool = SchoolEntity(
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

val dummyUser = UserEntity(
    phone = "testPhone",
    school = dummySchool,
    schoolGrade = 1,
    schoolClass = 1,
    name = "testName",
    gender = Gender.MALE,
    profileImageUrl = "profileImageUrl",
)