package com.ohayo.moyamoya.api

import com.ohayo.moyamoya.core.Gender
import com.ohayo.moyamoya.core.SchoolEntity
import com.ohayo.moyamoya.core.SchoolType
import com.ohayo.moyamoya.core.UserEntity
import java.time.LocalDate

val dummySchool = SchoolEntity(
    id = 0,
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
    id = 0,
    phone = "testPhone",
    school = dummySchool,
    schoolGrade = 1,
    schoolClass = 1,
    name = "testName",
    gender = Gender.MALE,
    profileImageUrl = "profileImageUrl",
)