package com.example.tp3_parcial_grupo_1.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "exercises",
    indices = [Index("workout_id")],
    primaryKeys = ["workout_id", "id"],
    foreignKeys = [ForeignKey(
        entity = WorkoutEntity::class,
        parentColumns = ["id"],
        childColumns = ["workout_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExerciseEntity(
    val id: String,
    @ColumnInfo(name = "workout_id")
    val workoutId: String,
    val name: String,
    val sets: Int,
    val reps: Int,
    val muscleGroup: String,
    val restSeconds: Int,
    val tip: String,
    val instructions: String
)