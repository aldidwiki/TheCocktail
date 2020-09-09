package com.aldidwikip.thecocktail.di

import android.content.Context
import androidx.room.Room
import com.aldidwikip.thecocktail.data.local.LocalDatabase
import com.aldidwikip.thecocktail.data.local.LocalDatabase.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase =
            Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton
    @Provides
    fun provideLocalService(database: LocalDatabase) = database.localService()
}