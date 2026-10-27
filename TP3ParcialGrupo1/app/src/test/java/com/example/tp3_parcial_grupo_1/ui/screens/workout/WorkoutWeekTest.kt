package com.example.tp3_parcial_grupo_1.ui.screens.workout

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class WorkoutWeekTest {

    private val tuesday = LocalDate.of(2026, 10, 27)

    @Test
    fun `given a date, when building its week, then it runs from monday to sunday`() {
        val week = weekOf(tuesday)

        assertEquals(7, week.size)
        assertEquals(LocalDate.of(2026, 10, 26), week.first().date)
        assertEquals(LocalDate.of(2026, 11, 1), week.last().date)
        assertEquals(listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"), week.map { it.name })
        assertEquals(listOf("M", "T", "W", "T", "F", "S", "S"), week.map { it.initial })
    }

    @Test
    fun `given a week that crosses two months, when reading the days, then each keeps its own date`() {
        val week = weekOf(tuesday)

        assertEquals(listOf("26", "27", "28", "29", "30", "31", "1"), week.map { it.number })
        assertEquals(LocalDate.of(2026, 11, 1), week.last().date)
    }

    @Test
    fun `given a sunday, when building its week, then it belongs to the week that ends that day`() {
        val week = weekOf(LocalDate.of(2026, 11, 1))

        assertEquals(LocalDate.of(2026, 10, 26), week.first().date)
    }

    @Test
    fun `given a valid date from the route, when parsing it, then that date is used`() {
        assertEquals(LocalDate.of(2026, 10, 29), parsePlanDate("2026-10-29", tuesday))
    }

    @Test
    fun `given no date or an invalid one, when parsing it, then today is used`() {
        assertEquals(tuesday, parsePlanDate(null, tuesday))
        assertEquals(tuesday, parsePlanDate("", tuesday))
        assertEquals(tuesday, parsePlanDate("8", tuesday))
    }
}
