package com.devrimcatak.egitim.model

import com.devrimcatak.egitim.model.enums.EventBusType


/**
 * Created by @Devrim Çatak on 6.07.2022.
 */
data class EventBusModel(
    val name : EventBusType?,
    val value : String?,
    val booleanValue : Boolean?
)
