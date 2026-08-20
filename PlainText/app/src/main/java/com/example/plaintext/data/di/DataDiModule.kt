package com.example.plaintext.data.di


import android.content.Context
import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.dao.PreferencesDao
import com.example.plaintext.data.repository.LocalPasswordDBStore
import com.example.plaintext.data.repository.PasswordDBStore
import com.example.plaintext.ui.screens.hello.dbSimulator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataDiModule {

    @Provides
    @Singleton
    fun providePasswordDao(@ApplicationContext context: Context): PasswordDao {
        return PasswordDao(context)
    }

    @Provides
    @Singleton
    fun providePreferencesDao(@ApplicationContext context: Context): PreferencesDao {
        return PreferencesDao(context)
    }

    @Provides
    @Singleton
    fun providePasswordStore(
        passwordDao: PasswordDao
    ): PasswordDBStore = LocalPasswordDBStore(passwordDao)

    @Provides
	@Singleton
	fun provideDBSimulator(): dbSimulator = dbSimulator()
}