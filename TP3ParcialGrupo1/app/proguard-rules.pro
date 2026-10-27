# Reglas de conservación del build de release (R8).
# Retrofit, OkHttp, Room y Hilt traen sus propias reglas de consumidor; acá van las que dependen del código de la app.

# Gson arma los DTOs por reflexión: hay que conservar los campos con @SerializedName y los genéricos de DataResponse<T>.
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod
-keep class com.example.tp3_parcial_grupo_1.data.remote.dto.** { *; }
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Retrofit lee los tipos genéricos de las funciones suspend de las interfaces de la API.
-keep,allowobfuscation,allowshrinking interface com.example.tp3_parcial_grupo_1.data.remote.api.**
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowobfuscation,allowshrinking class retrofit2.Response
