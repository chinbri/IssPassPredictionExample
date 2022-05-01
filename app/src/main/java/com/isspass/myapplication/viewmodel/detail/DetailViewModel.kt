package com.isspass.myapplication.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.domain.usecase.number.GetFactForNumberUseCase
import com.isspass.myapplication.ui.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getFactForNumberUseCase: GetFactForNumberUseCase
    ): ViewModel() {

    private var _factForNumber = MutableLiveData<String>()
    val factForNumber: LiveData<String>
        get() = _factForNumber

    private var _uiStatus = MutableLiveData<UiStatus>()
    val uiStatus: LiveData<UiStatus>
        get() = _uiStatus

    fun initialize(item: IssLocationItemEntity){

        if(item.fact == null){

            _uiStatus.value = UiStatus.Loading

            viewModelScope.launch {

                val factResult = getFactForNumberUseCase(item.duration.toInt())

                when (factResult){

                    is UseCaseResponse.Error<String> -> {
                        _uiStatus.value = UiStatus.Error(factResult.message)
                    }

                    is UseCaseResponse.Response<String> -> {
                        _uiStatus.value = UiStatus.Success
                        item.fact = factResult.value //save data in entity model in order to avoid repeat api calls
                        _factForNumber.value = factResult.value
                    }

                }

            }

        }else{
            _factForNumber.value = item.fact!!
        }

    }

}