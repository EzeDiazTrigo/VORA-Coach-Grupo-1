package com.example.tp3_parcial_grupo_1.ui.screens.home

import com.example.tp3_parcial_grupo_1.data.repository.NotificationRepository
import com.example.tp3_parcial_grupo_1.util.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NotificationsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Lazy: el ViewModel debe crearse después de que la regla fije Dispatchers.Main.
    private val viewModel by lazy { NotificationsViewModel(NotificationRepository()) }

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
