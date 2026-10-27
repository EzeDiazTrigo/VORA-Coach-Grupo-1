package com.example.tp3_parcial_grupo_1.ui.components

import org.junit.Assert.assertEquals
import org.junit.Test

class AppIconsTest {

    @Test
    fun `given the icon registry, when reading its resources, then no drawable is registered twice`() {
        val resources = AppIcons.entries.map { it.res }
        assertEquals(resources.size, resources.toSet().size)
    }

    @Test
    fun `given the icon registry, when filtering multicolor icons, then only the logos and the badge keep their colors`() {
        val multicolor = AppIcons.entries.filter { it.multicolor }.toSet()
        assertEquals(setOf(AppIcons.LogoApple, AppIcons.LogoGoogle, AppIcons.BadgeCompleted), multicolor)
    }
}
