package com.isspass.myapplication.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isspass.data.model.ApiResponseModel
import com.isspass.data.model.IssLocationResponseModel
import com.isspass.domain.UseCase
import com.isspass.domain.location.GetIssPredLocationParam
import com.isspass.domain.location.GetIssPredLocationUseCase
import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.model.iss.IssPredictedDataEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val getIssPredLocationUseCase: GetIssPredLocationUseCase): ViewModel() {

    private var _issPredictedData = MutableLiveData<IssPredictedDataEntity>()
    val issPredictedData: LiveData<IssPredictedDataEntity>
        get() = _issPredictedData

    private var _testMessage = MutableLiveData<String>()
    val testMessage: LiveData<String>
        get() = _testMessage

    fun onLocationObtained(location: Location){

        runBlocking {
            val result = getIssPredLocationUseCase(
                GetIssPredLocationParam(
                    location.latitude.toLong(),
                    location.longitude.toLong(),
                    location.altitude.toLong()
                )
            )

            when(result){
                is UseCaseResponse.Error -> {
                    _testMessage.value = result.message
                }
                is UseCaseResponse.Response<IssPredictedDataEntity> -> {
                    _issPredictedData.value = result.value
                }
            }

        }

    }

}