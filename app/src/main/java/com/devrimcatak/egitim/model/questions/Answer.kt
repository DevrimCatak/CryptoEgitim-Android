package com.devrimcatak.egitim.model.questions

import com.devrimcatak.egitim.model.enums.QuestionAnswersType

data class Answer(
    val answer: String?,
    val correct: String?,
    val id: Int?,
    val question_id: String?,
    var answersType: QuestionAnswersType = QuestionAnswersType.Review
)