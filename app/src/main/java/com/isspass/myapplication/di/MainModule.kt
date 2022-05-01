package com.isspass.myapplication.di

import com.isspass.data.network.location.GetIssPredLocationNetworkRepository
import com.isspass.data.network.location.GetIssPredLocationNetworkRepositoryImpl
import com.isspass.data.network.ApiService
import com.isspass.data.network.NumbersApiService
import com.isspass.data.network.numbers.NumbersDataNetworkRepository
import com.isspass.data.network.numbers.NumbersDataNetworkRepositoryImpl
import com.isspass.domain.usecase.location.GetIssPredLocationUseCase
import com.isspass.domain.usecase.location.GetIssPredLocationUseCaseImpl
import com.isspass.domain.usecase.number.GetFactForNumberUseCase
import com.isspass.domain.usecase.number.GetFactForNumberUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainModule {

    @Provides
    fun provideGetIssPredLocationUseCase(
        getIssPredLocationNetworkRepository: GetIssPredLocationNetworkRepository
    ): GetIssPredLocationUseCase = GetIssPredLocationUseCaseImpl(getIssPredLocationNetworkRepository)

    @Provides
    fun provideGetFactForNumberUseCase(
        numbersDataNetworkRepository: NumbersDataNetworkRepository
    ): GetFactForNumberUseCase = GetFactForNumberUseCaseImpl(numbersDataNetworkRepository)

    @Provides
    fun provideGetIssPredLocationNetworkRepository(apiService: ApiService): GetIssPredLocationNetworkRepository = GetIssPredLocationNetworkRepositoryImpl(apiService)

    @Provides
    fun provideNumbersDataNetworkRepository(apiService: NumbersApiService): NumbersDataNetworkRepository = NumbersDataNetworkRepositoryImpl(apiService)


}