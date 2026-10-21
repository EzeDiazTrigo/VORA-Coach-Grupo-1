package com.example.tp3_parcial_grupo_1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val level: String,
    val durationMinutes: Int,
    val exerciseCount: Int,
    val description: String? = null
)