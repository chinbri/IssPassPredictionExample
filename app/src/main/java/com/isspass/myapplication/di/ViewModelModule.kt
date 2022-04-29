package com.isspass.myapplication.di

import com.example.domain.iss.GetIssPredLocationUseCase
import com.example.domain.iss.GetIssPredLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideGetIssPredLocationUseCase(): GetIssPredLocationUseCase = GetIssPredLocationUseCaseImpl()

}