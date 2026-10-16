package com.example.tp3_parcial_grupo_1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "workout_id")
    val workoutId: String,
    val name: String,
    val sets: Int,
    val reps: Int,
    val instructions: String? = null
)