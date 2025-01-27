package com.ohayo.moyamoya.core.user.profile

import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.global.CustomException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : JpaRepository<UserProfileEntity, Int> {
    fun findByUser(user: UserEntity): UserProfileEntity?

    @Query("SELECT up FROM UserProfileEntity up WHERE up.user.school.id = :schoolId")
    fun findBySchoolId(schoolId: Int): List<UserProfileEntity>
}

fun UserProfileRepository.findByUserSafety(user: UserEntity) = findByUser(user)
    ?: throw CustomException(HttpStatus.NOT_FOUND, "유저 프로필을 찾을 수 없습니다")
