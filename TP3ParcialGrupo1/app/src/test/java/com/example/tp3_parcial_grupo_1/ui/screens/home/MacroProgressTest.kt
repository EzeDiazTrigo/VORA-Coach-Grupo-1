package com.example.tp3_parcial_grupo_1.ui.screens.home

import org.junit.Assert.assertEquals
import org.junit.Test

class MacroProgressTest {
    @Test
    fun `fraction is current over goal`() {
        assertEquals(0.5f, MacroProgress(current = 70, goal = 140).fraction, 0.001f)
    }

    @Test
    fun `fraction never exceeds one`() {
        assertEquals(1f, MacroProgress(current = 200, goal = 140).fraction, 0.001f)
    }

    @Test
    fun `zero goal gives zero fraction`() {
        assertEquals(0f, MacroProgress(current = 10, goal = 0).fraction, 0.001f)
    }
}
