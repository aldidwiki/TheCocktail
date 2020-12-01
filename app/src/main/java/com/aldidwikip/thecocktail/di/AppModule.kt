package com.aldidwikip.thecocktail.di

import android.content.Context
import android.content.SharedPreferences
import com.aldidwikip.thecocktail.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences(context.resources.getString(R.string.preference_name), Context.MODE_PRIVATE)
}