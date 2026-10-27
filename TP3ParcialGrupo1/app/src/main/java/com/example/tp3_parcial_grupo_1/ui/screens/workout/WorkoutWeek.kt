package com.example.tp3_parcial_grupo_1.ui.screens.workout

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/** Un día de la semana de Workout: «MON 26». La fecha completa evita confundir el 1 de un mes con el 1 del siguiente. */
data class WeekDay(val date: LocalDate) {
    val name: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase()
    val initial: String = name.take(1)
    val number: String = date.dayOfMonth.toString()
}

/** Los siete días, de lunes a domingo, de la semana que contiene [anchor]. */
fun weekOf(anchor: LocalDate): List<WeekDay> {
    val monday = anchor.with(DayOfWeek.MONDAY)
    return List(DaysInWeek) { WeekDay(monday.plusDays(it.toLong())) }
}

/** La fecha que llega por la ruta («2026-10-27»); sin fecha o con una inválida, hoy. */
fun parsePlanDate(raw: String?, today: LocalDate): LocalDate =
    raw?.let { runCatching { LocalDate.parse(it) }.getOrNull() } ?: today

private const val DaysInWeek = 7
