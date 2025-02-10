package com.ohayo.moyamoya.legacy

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
//        val school = schoolRepository.save(testSchool1)
//        val user1 = userRepository.save(testUser1)
//        val user2 = userRepository.save(testUser2)

        school = school
        user1 = user1
        user2 = user2
        user1Token = jwtClient.generate(user1)
        user2Token = jwtClient.generate(user2)
    }
}