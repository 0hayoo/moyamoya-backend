package com.ohayo.moyamoya.api.play

import com.ohayo.moyamoya.api.play.value.PlayRes
import com.ohayo.moyamoya.api.user.profile.core.MatchingHelper
import com.ohayo.moyamoya.api.user.profile.core.MatchingResult
import com.ohayo.moyamoya.core.play.*
import com.ohayo.moyamoya.core.play.PlayEntity
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.user.profile.UserProfileRepository
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class PlayService(
    private val userProfileRepository: UserProfileRepository,
    private val playRepository: PlayRepository,
    private val sessionHolder: UserSessionHolder,
    private val playEventRepository: PlayEventRepository,
    private val playEventQuestionRepository: QuestionRepository
) {
    fun getTodayPlay(): PlayRes {
        val user = sessionHolder.current()

        // 이미 플레이 중인 게 있다면 반환
        val oldPlay = playRepository.findByNonExpired(user.id)
        if (oldPlay != null) {
            return PlayRes.of(oldPlay)
        }

        val users = userProfileRepository.findBySchoolId(user.school.id)
        val result = MatchingHelper.matchUsers(users)
            .let { it.forEach(::println);it }.firstOrNull {
                it.male.user.id == user.id || it.female.user.id == user.id
            }

        // 매칭 상대가 없는 경우 404
        if (result == null) {
            throw CustomException(HttpStatus.NOT_FOUND, "매칭 실패. 상대가 없습니다")
        }

        // 오늘 플레이 저장 
        val createdPlay = createPlay(result)
        return PlayRes.of(createdPlay)
    }

    private fun createPlay(result: MatchingResult): PlayEntity {
        val play = playRepository.save(
            PlayEntity(
                male = result.male,
                female = result.female,
                score = result.score,
            )
        )
        
//        val questions = playEventQuestionRepository.findRandom(3)
//        
//        val playEvent = playEventRepository.saveAll()
        
        return play
    }
}