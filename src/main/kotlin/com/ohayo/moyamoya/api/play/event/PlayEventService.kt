package com.ohayo.moyamoya.api.play.event

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.play.event.value.*
import com.ohayo.moyamoya.core.answer.AnswerEntity
import com.ohayo.moyamoya.core.answer.AnswerRepository
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.core.play.*
import com.ohayo.moyamoya.core.question.QuestionEntity
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class PlayEventService(
    private val playEventRepository: PlayEventRepository,
    private val sessionHolder: UserSessionHolder,
    private val playRepository: PlayRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val playEventReviewRepository: PlayEventReviewRepository,
) {
    fun getPlayEvents(playId: Int): List<PlayEventRes> {
        val (_, play) = validatePlay(playId)
        val playEvents = playEventRepository.findByPlay(play)

        return playEvents.map(PlayEventRes::of)
    }

    fun getQuestions(playEventId: Int): List<QuestionRes> {
        val (_, playEvent) = validatePlayEvent(playEventId)

        if (playEvent.eventTime.isAfter(LocalDateTime.now())) {
            throw CustomException(HttpStatus.BAD_REQUEST, "이벤트 오픈 전입니다")
        }

        return questionRepository.findBySubject(playEvent.subject)
            .map(QuestionRes::of)
    }

    // todo 성능
    fun answerQuestion(req: List<AnswerQuestionReq>): VoidRes {
        val user = sessionHolder.current()

        val answers = req.map {
            val question = questionRepository.findByIdSafety(it.questionId)
            val playEvent = playEventRepository.findBySubjectSafety(question.subject)
            playEvent.assertJoinedUser(user.id)

            AnswerEntity(
                content = it.content,
                user = user,
                question = question
            )
        }

        answerRepository.saveAll(answers)
        return VoidRes()
    }

    fun getAnswers(playEventId: Int): AnswersRes {
        val (_, playEvent) = validatePlayEvent(playEventId)
        val questions = getQuestions(playEvent)

        val maleAnswers = validateAnswers(playEvent.play.male.user, questions)
        val femaleAnswers = validateAnswers(playEvent.play.female.user, questions)

        return AnswersRes.of(
            maleAnswers = maleAnswers,
            femaleAnswers = femaleAnswers
        )
    }

    fun reviewEvent(playEventId: Int, req: CreatePlayEventReviewReq): VoidRes {
        val (user, playEvent) = validatePlayEvent(playEventId)
        val questions = getQuestions(playEvent)

        validateAnswers(playEvent.play.male.user, questions)
        validateAnswers(playEvent.play.female.user, questions)

        playEventReviewRepository.save(req.toEntity(user, playEvent))
        
        // todo: add score with req.star

        return VoidRes()
    }

    private fun validatePlay(playId: Int): Pair<UserEntity, PlayEntity> {
        val play = playRepository.findByIdSafety(playId)
        val user = sessionHolder.current()
        play.assertJoinedUser(user.id)

        return Pair(user, play)
    }

    private fun validatePlayEvent(playEventId: Int): Pair<UserEntity, PlayEventEntity> {
        val user = sessionHolder.current()
        val playEvent = playEventRepository.findByIdSafety(playEventId)
        playEvent.assertJoinedUser(user.id)

        return Pair(user, playEvent)
    }

    private fun getQuestions(playEvent: PlayEventEntity) =
        questionRepository.findBySubject(playEvent.subject)

    private fun validateAnswers(user: UserEntity, questions: List<QuestionEntity>): List<AnswerEntity> {
        val answers = answerRepository.findByUserAndQuestionIn(user, questions)
        if (answers.isEmpty()) {
            throw CustomException(HttpStatus.BAD_REQUEST, "아직 답변이 완료되지 않았습니다")
        }
        return answers
    }
}