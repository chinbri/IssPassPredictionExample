package com.isspass.myapplication.di

import com.isspass.data.network.location.GetIssPredLocationNetworkRepository
import com.isspass.data.network.location.GetIssPredLocationNetworkRepositoryImpl
import com.isspass.data.network.ApiService
import com.isspass.data.network.NumbersApiService
import com.isspass.data.network.numbers.NumbersDataNetworkRepository
import com.isspass.data.network.numbers.NumbersDataNetworkRepositoryImpl
import com.isspass.domain.location.GetIssPredLocationUseCase
import com.isspass.domain.location.GetIssPredLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainModule {

    @Provides
    fun provideGetIssPredLocationUseCase(
        getIssPredLocationNetworkRepository: GetIssPredLocationNetworkRepository,
        numbersDataNetworkRepository: NumbersDataNetworkRepository
    ): GetIssPredLocationUseCase = GetIssPredLocationUseCaseImpl(getIssPredLocationNetworkRepository, numbersDataNetworkRepository)

    @Provides
    fun provideGetIssPredLocationNetworkRepository(apiService: ApiService): GetIssPredLocationNetworkRepository = GetIssPredLocationNetworkRepositoryImpl(apiService)

    @Provides
    fun provideNumbersDataNetworkRepository(apiService: NumbersApiService): NumbersDataNetworkRepository = NumbersDataNetworkRepositoryImpl(apiService)

}