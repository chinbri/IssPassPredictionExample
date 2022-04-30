package com.isspass.myapplication.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isspass.domain.location.GetIssPredLocationParam
import com.isspass.domain.location.GetIssPredLocationUseCase
import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.model.iss.IssPredictedDataEntity
import com.isspass.myapplication.ui.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val getIssPredLocationUseCase: GetIssPredLocationUseCase): ViewModel() {

    private var _issPredictedData = MutableLiveData<IssPredictedDataEntity>()
    val issPredictedData: LiveData<IssPredictedDataEntity>
        get() = _issPredictedData

    private var _uiStatus = MutableLiveData<UiStatus>()
    val uiStatus: LiveData<UiStatus>
        get() = _uiStatus

    fun onLocationObtained(location: Location){

        _issPredictedData.value = IssPredictedDataEntity(emptyList())

        _uiStatus.value = UiStatus.Loading

        viewModelScope.launch {

            val result = getIssPredLocationUseCase(
                GetIssPredLocationParam(
                    location.latitude.toLong(),
                    location.longitude.toLong(),
                    location.altitude.toLong()
                )
            )

            when(result){
                is UseCaseResponse.Error -> {
                    _uiStatus.value = UiStatus.Error(result.message)
                }
                is UseCaseResponse.Response<IssPredictedDataEntity> -> {
                    _uiStatus.value = UiStatus.Success
                    _issPredictedData.value = result.value
                }
            }
        }

    }

}