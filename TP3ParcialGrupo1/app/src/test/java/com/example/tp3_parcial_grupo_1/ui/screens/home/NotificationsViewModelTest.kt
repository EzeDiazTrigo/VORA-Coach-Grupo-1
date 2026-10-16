package com.example.tp3_parcial_grupo_1.ui.screens.home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NotificationsViewModelTest {
    private val viewModel = NotificationsViewModel()

    @Test
    fun `starts with unread notifications`() {
        assertTrue(viewModel.uiState.value.hasUnread)
    }

    @Test
    fun `mark all as read clears every unread`() {
        viewModel.onMarkAllAsReadClick()

        assertFalse(viewModel.uiState.value.hasUnread)
    }

    @Test
    fun `clicking a notification marks only that one as read`() {
        val unreadBefore = viewModel.uiState.value.items.count { it.isUnread }
        val target = viewModel.uiState.value.items.first { it.isUnread }

        viewModel.onNotificationClick(target.id)

        assertEquals(unreadBefore - 1, viewModel.uiState.value.items.count { it.isUnread })
    }
}
