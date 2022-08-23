package com.devrimcatak.egitim.ui.lesson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.lesson.Lesson
import com.devrimcatak.egitim.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */

@HiltViewModel
class LessonViewModel @Inject constructor(private val repository: LessonRepository): ViewModel() {


}