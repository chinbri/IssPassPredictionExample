package com.isspass.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.iss.GetIssPredLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val getIssPredLocationUseCase: GetIssPredLocationUseCase): ViewModel() {

    private var _testMessage = MutableLiveData<String>()

    val testMessage: LiveData<String>
        get() = _testMessage

    init {

        runBlocking {
            val result = getIssPredLocationUseCase("aa")
            _testMessage.value = result
        }

    }
}