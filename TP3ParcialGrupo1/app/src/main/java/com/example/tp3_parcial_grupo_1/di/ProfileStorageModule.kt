package com.example.tp3_parcial_grupo_1.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.tp3_parcial_grupo_1.data.local.DataStoreProfileStorage
import com.example.tp3_parcial_grupo_1.data.local.ProfileStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val ProfileDataStoreName = "profile"

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileStorageModule {

    @Binds
    abstract fun bindProfileStorage(storage: DataStoreProfileStorage): ProfileStorage

    companion object {
        @Provides
        @Singleton
        fun provideProfileDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile(ProfileDataStoreName) })
    }
}
