package com.devrimcatak.egitim.model.questions

data class Data(
    val answers: List<Answer>?,
    val id: Int?,
    val lesson_id: String?,
    val question: String?
)