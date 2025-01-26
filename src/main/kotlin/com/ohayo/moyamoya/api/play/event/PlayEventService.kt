package com.ohayo.moyamoya.api.play.event

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.play.event.value.AnswerQuestionReq
import com.ohayo.moyamoya.api.play.event.value.PlayEventRes
import com.ohayo.moyamoya.api.play.event.value.QuestionRes
import com.ohayo.moyamoya.core.answer.AnswerRepository
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.core.play.PlayEventRepository
import com.ohayo.moyamoya.core.play.PlayRepository
import com.ohayo.moyamoya.core.play.findBySubjectSafety
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.question.SubjectRepository
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class PlayEventService(
    private val playEventRepository: PlayEventRepository,
    private val sessionHolder: UserSessionHolder,
    private val playRepository: PlayRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val subjectRepository: SubjectRepository
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

            it.toEntity(
                user = sessionHolder.current(),
                question = question
            )
        }
        
        answerRepository.saveAll(answers)
        return VoidRes()
    }
}