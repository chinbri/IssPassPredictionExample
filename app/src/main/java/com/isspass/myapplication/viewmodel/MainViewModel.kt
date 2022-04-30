package com.isspass.myapplication.viewmodel

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

    private var _testMessage = MutableLiveData<String>()

    val testMessage: LiveData<String>
        get() = _testMessage

    init {

        runBlocking {
            val result = getIssPredLocationUseCase(
                GetIssPredLocationParam(
                    40.027435.toLong(),
                    -105.251945.toLong(),
                    1650.0.toLong()
                )
            )

            when(result){
                is UseCaseResponse.Error -> {
                    _testMessage.value = result.message
                }
                is UseCaseResponse.Response<IssPredictedDataEntity> -> {
                    _testMessage.value = result.value.locations.size.toString()
                }
            }

        }

    }
}