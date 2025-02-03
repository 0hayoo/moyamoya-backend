package com.ohayo.moyamoya.core.school

import com.ohayo.moyamoya.api.school.value.SchoolRes
import com.ohayo.moyamoya.api.school.value.SchoolWaitingCountRes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SchoolRepository : JpaRepository<SchoolEntity, Int> {
    @Query(
        """
    SELECT new com.ohayo.moyamoya.api.school.value.SchoolRes(
        s.id,
        s.name,
        s.type,
        s.cityName,
        s.postalCode,
        s.address,
        s.addressDetail,
        s.phone,
        s.website,
        s.foundedAt,
        s.anniversary,
        s.schoolCode,
        s.officeCode,
        CAST(COUNT(st.id) AS INTEGER)
    )
    FROM SchoolEntity s
    LEFT JOIN UserEntity st ON st.school.id = s.id
    GROUP BY s.id
    """
    )
    fun findAllResWithStudentCount(): List<SchoolRes>

    @Query(
        """
            SELECT new com.ohayo.moyamoya.api.school.value.SchoolWaitingCountRes(
                s.id,
                s.name,
                s.waitingCount
            )
            FROM SchoolEntity s
        """
    )
    fun findAllWaitingCountRes(): List<SchoolWaitingCountRes>
}