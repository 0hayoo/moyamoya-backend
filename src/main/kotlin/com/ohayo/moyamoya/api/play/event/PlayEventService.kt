package com.ohayo.moyamoya.api.play.event

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.play.event.value.*
import com.ohayo.moyamoya.core.answer.AnswerEntity
import com.ohayo.moyamoya.core.answer.AnswerRepository
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.core.play.PlayEventRepository
import com.ohayo.moyamoya.core.play.PlayRepository
import com.ohayo.moyamoya.core.play.findBySubjectSafety
import com.ohayo.moyamoya.core.question.QuestionRepository
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
) {
    fun getPlayEvents(playId: Int): List<PlayEventRes> {
        val play = playRepository.findByIdSafety(playId)
        val user = sessionHolder.current()
        play.assertJoinedUser(user.id)

        val playEvents = playEventRepository.findByPlay(play)

        return playEvents.map(PlayEventRes::of)
    }

    fun getQuestions(playEventId: Int): List<QuestionRes> {
        val user = sessionHolder.current()
        val playEvent = playEventRepository.findByIdSafety(playEventId)
        playEvent.assertJoinedUser(user.id)

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
        val user = sessionHolder.current()
        val playEvent = playEventRepository.findByIdSafety(playEventId)
        val play = playEvent.play
        playEvent.assertJoinedUser(user.id)

        val questions = questionRepository.findBySubject(playEvent.subject)
        
        val maleAnswer = answerRepository.findByUserAndQuestionIn(play.male.user, questions)
        if (maleAnswer.isEmpty()) {
            throw CustomException(HttpStatus.BAD_REQUEST, "아직 답변이 완료되지 않았습니다")
        }
        
        val femaleAnswer = answerRepository.findByUserAndQuestionIn(play.female.user, questions)
        if (femaleAnswer.isEmpty()) {
            throw CustomException(HttpStatus.BAD_REQUEST, "아직 답변이 완료되지 않았습니다")
        }
        
        return AnswersRes.of(
            maleAnswers = maleAnswer,
            femaleAnswers = femaleAnswer
        )
    }
}