package com.isspass.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private var _testMessage = MutableLiveData<String>()

    val testMessage: LiveData<String>
        get() = _testMessage

    init {
        _testMessage.value = "trying view model!"
    }
}