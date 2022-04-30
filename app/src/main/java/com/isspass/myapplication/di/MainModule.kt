package com.isspass.myapplication.di

import com.isspass.data.GetIssPredLocationNetworkRepository
import com.isspass.data.GetIssPredLocationNetworkRepositoryImpl
import com.isspass.data.network.ApiService
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
    fun provideGetIssPredLocationUseCase(getIssPredLocationNetworkRepository: GetIssPredLocationNetworkRepository): GetIssPredLocationUseCase = GetIssPredLocationUseCaseImpl(getIssPredLocationNetworkRepository)

    @Provides
    fun provideGetIssPredLocationNetworkRepository(apiService: ApiService): GetIssPredLocationNetworkRepository = GetIssPredLocationNetworkRepositoryImpl(apiService)

}