package com.aldidwikip.thecocktail.di

import com.aldidwikip.thecocktail.data.AppRepository
import com.aldidwikip.thecocktail.data.local.LocalService
import com.aldidwikip.thecocktail.data.remote.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
            remoteService: RemoteService,
            localService: LocalService
    ): AppRepository {
        return AppRepository(remoteService, localService)
    }
}