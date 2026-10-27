package com.example.tp3_parcial_grupo_1.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

// Guarda cada sección del perfil como un texto (JSON) bajo su propia clave.
interface ProfileStorage {
    suspend fun read(key: String): String?
    suspend fun write(key: String, value: String)
}

class InMemoryProfileStorage : ProfileStorage {
    private val values = mutableMapOf<String, String>()

    override suspend fun read(key: String): String? = values[key]

    override suspend fun write(key: String, value: String) {
        values[key] = value
    }
}

@Singleton
class DataStoreProfileStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ProfileStorage {

    override suspend fun read(key: String): String? = dataStore.data.first()[stringPreferencesKey(key)]

    override suspend fun write(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }
}
