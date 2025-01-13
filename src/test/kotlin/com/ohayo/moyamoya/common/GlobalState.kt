package com.ohayo.moyamoya.common

import com.ohayo.moyamoya.api.dummySchool
import com.ohayo.moyamoya.api.dummyUser
import com.ohayo.moyamoya.core.SchoolEntity
import com.ohayo.moyamoya.core.SchoolRepository
import com.ohayo.moyamoya.core.UserEntity
import com.ohayo.moyamoya.core.UserRepository
import com.ohayo.moyamoya.infra.token.JwtClient
import com.ohayo.moyamoya.infra.token.Token

object GlobalState {
    var token: Token? = null
    var school: SchoolEntity? = null
    var user: UserEntity? = null

    fun initToken(
        userRepository: UserRepository,
        schoolRepository: SchoolRepository,
        jwtClient: JwtClient
    ) {
        println("init token")
        val school = schoolRepository.save(dummySchool)
        val user = userRepository.save(dummyUser)

        this.school = school
        this.user = user

        token = jwtClient.generate(user)
    }
}