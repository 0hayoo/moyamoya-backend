package com.ohayo.moyamoya.api.play.event

import com.ohayo.moyamoya.api.common.VoidRes
import com.ohayo.moyamoya.api.play.event.value.AnswerQuestionReq
import com.ohayo.moyamoya.api.play.event.value.PlayEventRes
import com.ohayo.moyamoya.api.play.event.value.QuestionRes
import com.ohayo.moyamoya.core.answer.AnswerEntity
import com.ohayo.moyamoya.core.answer.AnswerRepository
import com.ohayo.moyamoya.core.extension.findByIdSafety
import com.ohayo.moyamoya.core.play.PlayEventRepository
import com.ohayo.moyamoya.core.play.PlayRepository
import com.ohayo.moyamoya.core.question.QuestionRepository
import com.ohayo.moyamoya.core.question.SubjectRepository
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.stereotype.Service

@Service
class PlayEventService(
    private val playEventRepository: PlayEventRepository,
    private val sessionHolder: UserSessionHolder,
    private val playRepository: PlayRepository,
    private val subjectRepository: SubjectRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository
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

        return playEvent.subject.questions.map(QuestionRes::of)
    }

    fun answerQuestion(playEventId: Int, questionId: Int, req: AnswerQuestionReq): VoidRes {
        val user = sessionHolder.current()
        val playEvent = playEventRepository.findByIdSafety(playEventId)
        playEvent.assertJoinedUser(user.id)

        answerRepository.save(
            AnswerEntity(
                answer = req.answer,
                user = sessionHolder.current(),
                playEvent = playEvent,
                question = questionRepository.findByIdSafety(questionId)
            )
        )
        
        return VoidRes()
    }
}