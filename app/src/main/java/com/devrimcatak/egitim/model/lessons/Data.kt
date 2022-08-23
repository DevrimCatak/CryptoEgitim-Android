package com.devrimcatak.egitim.model.lessons

import com.devrimcatak.egitim.model.enums.LessonsCompletedType

data class Data(
    val id: Int?,
    val banner: String?,
    val banner_type: String?,
    val completed: Int?,
    var completedType: LessonsCompletedType?,
    val detail: String?,
    val explanation: String?,
    val status: String?,
    val title: String?
)