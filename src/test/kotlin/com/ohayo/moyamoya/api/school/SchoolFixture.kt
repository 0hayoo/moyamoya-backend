package com.ohayo.moyamoya.api.school

import com.ohayo.moyamoya.core.school.SchoolEntity
import com.ohayo.moyamoya.core.school.SchoolType
import java.time.LocalDate

object SchoolFixture {
    val school1 = SchoolEntity(
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
}