package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.core.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserProfileRepository : JpaRepository<UserProfileEntity, Int> {
    fun findByUser(user: UserEntity): UserProfileEntity?

    @Query("SELECT up FROM UserProfileEntity up WHERE up.user.school.id = :schoolId")
    fun findBySchoolId(schoolId: Int): List<UserProfileEntity>
}