package com.example.tp3_parcial_grupo_1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Historial del chat con VORA. author: "user" o "vora"; createdAt en milisegundos.
@Entity(tableName = "coach_messages")
data class CoachMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String,
    val text: String,
    val createdAt: Long,
    val audioSeconds: Int? = null
)
