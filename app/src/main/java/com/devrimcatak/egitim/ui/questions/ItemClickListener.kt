package com.devrimcatak.egitim.ui.questions

import com.devrimcatak.egitim.model.questions.Answer
import com.devrimcatak.egitim.model.questions.Data


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */
interface ItemClickListener {
    fun onItemClick(answers: Answer)
}