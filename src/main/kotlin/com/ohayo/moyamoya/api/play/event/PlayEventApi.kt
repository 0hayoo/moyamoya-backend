package com.ohayo.moyamoya.api.play.event

import com.ohayo.moyamoya.api.play.event.value.AnswerQuestionReq
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plays/events")
class PlayEventApi(
    private val playEventService: PlayEventService
) {
    @GetMapping
    fun getPlayEvents(
        @RequestParam playId: Int
    ) = playEventService.getPlayEvents(playId = playId)

    @GetMapping("/{playEventId}/questions")
    fun getQuestions(
        @PathVariable playEventId: Int,
    ) = playEventService.getQuestions(playEventId)

    @PostMapping("/{playEventId}/questions/{questionId}/answer")
    fun answerQuestion(
        @PathVariable playEventId: Int,
        @PathVariable questionId: Int,
        @RequestBody @Valid req: AnswerQuestionReq
    ) = playEventService.answerQuestion(playEventId, questionId, req)
}