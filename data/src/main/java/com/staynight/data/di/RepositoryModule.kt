package com.staynight.data.di

import android.content.SharedPreferences
import com.staynight.data.repository.StorageRepositoryImpl
import com.staynight.domain.repository.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStorageRepository(sharedPreferences: SharedPreferences): StorageRepository =
        StorageRepositoryImpl(sharedPreferences)
}