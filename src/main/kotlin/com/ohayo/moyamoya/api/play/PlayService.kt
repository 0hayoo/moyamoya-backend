package com.ohayo.moyamoya.api.play

import com.ohayo.moyamoya.api.play.value.PlayRes
import com.ohayo.moyamoya.api.play.core.MatchingHelper
import com.ohayo.moyamoya.api.play.core.MatchingResult
import com.ohayo.moyamoya.core.play.*
import com.ohayo.moyamoya.core.play.PlayEntity
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.question.SubjectEntity
import com.ohayo.moyamoya.core.user.profile.UserProfileRepository
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import com.ohayo.moyamoya.infra.googlesheets.GoogleSpreadSheetsClient
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class PlayService(
    private val userProfileRepository: UserProfileRepository,
    private val playRepository: PlayRepository,
    private val sessionHolder: UserSessionHolder,
    private val playEventRepository: PlayEventRepository,
    private val questionRepository: QuestionRepository,
    private val googleSpreadSheetsClient: GoogleSpreadSheetsClient
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

        // -- 질문 이벤트 생성 --
        val now = LocalDateTime.now()

        // 1시가 지났는지 확인 (현재 시간이 오전 1시 이후이면 오늘로, 아니면 어제로 설정)
        val baseTime = if (now.hour >= 1) now else now.minusDays(1)

        val subjectEventTimes = listOf(
            baseTime.withHour(8).withMinute(0).withSecond(0).withNano(0),
            baseTime.withHour(12).withMinute(30).withSecond(0).withNano(0), // 오전 12시 반 - 점심시간
            baseTime.withHour(16).withMinute(0).withSecond(0).withNano(0), // 오후 4시 - 하교 시간 or 자습 시간
            baseTime.withHour(20).withMinute(0).withSecond(0).withNano(0) // 오후 8시 - 귀가 시간
        )

        val gssSubjects = googleSpreadSheetsClient.readSubjects()
            .shuffled()
            .take(4) // 질문 4개

        val subjects = gssSubjects.map { gssSubject ->
            val subject = SubjectEntity(title = gssSubject.title)
            val questions = gssSubject.questions.map { it.toEntity(subject) }
            questionRepository.saveAll(questions)
                .first()
                .subject
        }

        val events = subjects.mapIndexed { index, subject ->
            val eventTime = subjectEventTimes[index]
            PlayEventEntity(
                eventTime = eventTime,
                play = play,
                subject = subject
            )
        }
        playEventRepository.saveAll(events)

        return play
    }
}