package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.data.local.InMemoryProfileStorage
import com.example.tp3_parcial_grupo_1.data.local.ProfileStorage
import com.example.tp3_parcial_grupo_1.model.NutritionPreferences
import com.example.tp3_parcial_grupo_1.model.UserProfile
import com.example.tp3_parcial_grupo_1.model.VoiceSettings
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private const val ProfileKey = "profile_v1"
private const val NutritionKey = "nutrition_preferences_v1"
private const val VoiceKey = "voice_settings_v1"

/**
 * Perfil y preferencias que comparten el Dashboard y las pantallas de edición. Arrancan con los datos de
 * demostración, cargan lo guardado en [ProfileStorage] y guardan cada cambio. Los StateFlow se actualizan al
 * instante; la lectura y la escritura van en segundo plano, y una lectura tardía nunca pisa un cambio hecho antes.
 */
@Singleton
class ProfileRepository internal constructor(
    private val storage: ProfileStorage,
    ioDispatcher: CoroutineDispatcher
) {
    @Inject
    constructor(storage: ProfileStorage) : this(storage, Dispatchers.IO)

    // Para pruebas y vistas previas: nada se guarda.
    constructor() : this(InMemoryProfileStorage())

    private val gson = Gson()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher.limitedParallelism(1))

    private val _profile = MutableStateFlow(ProfileDemoData.profile)
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    private val _nutritionPreferences = MutableStateFlow(ProfileDemoData.nutritionPreferences)
    val nutritionPreferences: StateFlow<NutritionPreferences> = _nutritionPreferences.asStateFlow()

    private val _voiceSettings = MutableStateFlow(ProfileDemoData.voiceSettings)
    val voiceSettings: StateFlow<VoiceSettings> = _voiceSettings.asStateFlow()

    @Volatile private var profileEdited = false
    @Volatile private var nutritionEdited = false
    @Volatile private var voiceEdited = false

    init {
        load(ProfileKey, UserProfile::class.java, _profile) { !profileEdited }
        load(NutritionKey, NutritionPreferences::class.java, _nutritionPreferences) { !nutritionEdited }
        load(VoiceKey, VoiceSettings::class.java, _voiceSettings) { !voiceEdited }
    }

    fun update(transform: (UserProfile) -> UserProfile) {
        profileEdited = true
        _profile.update(transform)
        save(ProfileKey, _profile.value)
    }

    fun updateNutritionPreferences(transform: (NutritionPreferences) -> NutritionPreferences) {
        nutritionEdited = true
        _nutritionPreferences.update(transform)
        save(NutritionKey, _nutritionPreferences.value)
    }

    fun updateVoiceSettings(transform: (VoiceSettings) -> VoiceSettings) {
        voiceEdited = true
        _voiceSettings.update(transform)
        save(VoiceKey, _voiceSettings.value)
    }

    private fun <T : Any> load(key: String, type: Class<T>, target: MutableStateFlow<T>, canApply: () -> Boolean) {
        scope.launch {
            // Un dato guardado que no se puede leer (por ejemplo de otra versión) se ignora y quedan los de demostración.
            val stored = runCatching { storage.read(key)?.let { gson.fromJson(it, type) } }.getOrNull()
            if (stored != null && canApply()) target.value = stored
        }
    }

    private fun save(key: String, value: Any) {
        val json = gson.toJson(value)
        scope.launch { runCatching { storage.write(key, json) } }
    }
}
