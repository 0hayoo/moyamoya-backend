package com.ohayo.moyamoya.common

import com.ohayo.moyamoya.api.testSchool1
import com.ohayo.moyamoya.api.testUser1
import com.ohayo.moyamoya.api.testUser2

import com.ohayo.moyamoya.core.school.SchoolEntity
import com.ohayo.moyamoya.core.school.SchoolRepository
import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.core.user.UserRepository
import com.ohayo.moyamoya.infra.token.JwtClient
import com.ohayo.moyamoya.infra.token.Token

object GlobalState {
    lateinit var user1Token: Token
        private set
    lateinit var user2Token: Token
        private set
    lateinit var school: SchoolEntity
        private set
    lateinit var user1: UserEntity
        private set
    lateinit var user2: UserEntity
        private set

    fun initToken(
        userRepository: UserRepository,
        schoolRepository: SchoolRepository,
        jwtClient: JwtClient
    ) {
        println("init token")
        val school = schoolRepository.save(testSchool1)
        val user1 = userRepository.save(testUser1)
        val user2 = userRepository.save(testUser2)

        this.school = school
        this.user1 = user1
        this.user2 = user2
        this.user1Token = jwtClient.generate(user1)
        this.user2Token = jwtClient.generate(user2)
    }
}