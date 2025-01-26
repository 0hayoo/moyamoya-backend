package com.ohayo.moyamoya.infra.googlesheets.value

data class GSSSubjectRes(
    val title: String,
    val questions: List<GSSQuestionRes>
)