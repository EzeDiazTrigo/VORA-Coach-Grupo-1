package com.example.tp3_parcial_grupo_1.ui.strings

import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileLabelsTest {

    @Test
    fun `given one goal, when summarizing, then it is shown as its title`() {
        assertEquals("Lose body fat", setOf(PrimaryGoal.LoseBodyFat).summary())
    }

    @Test
    fun `given two goals, when summarizing, then they are joined with an ampersand as in Figma`() {
        assertEquals(
            "Build strength & improve fitness",
            setOf(PrimaryGoal.ImproveFitness, PrimaryGoal.BuildStrength).summary()
        )
    }

    @Test
    fun `given three goals, when summarizing, then commas separate all but the last`() {
        assertEquals(
            "Build strength, improve fitness & lose body fat",
            setOf(PrimaryGoal.LoseBodyFat, PrimaryGoal.ImproveFitness, PrimaryGoal.BuildStrength).summary()
        )
    }

    @Test
    fun `given no goals, when summarizing, then it is empty`() {
        assertEquals("", emptySet<PrimaryGoal>().summary())
    }
}
