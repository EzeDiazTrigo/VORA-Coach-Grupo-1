package com.example.tp3_parcial_grupo_1.ui.screens.home

import com.example.tp3_parcial_grupo_1.data.repository.NotificationRepository
import com.example.tp3_parcial_grupo_1.util.MainDispatcherRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = NotificationRepository()
    // Lazy: el ViewModel debe crearse después de que la regla fije Dispatchers.Main.
    private val viewModel by lazy { HomeViewModel(repository) }

    @Test
    fun `given unread notifications, when home starts, then the bell shows the unread dot`() {
        assertTrue(viewModel.uiState.value.hasUnreadNotifications)
    }

    @Test
    fun `given every notification read from notifications, when home observes, then the unread dot hides`() {
        val notifications = NotificationsViewModel(repository)
        viewModel.uiState.value

        notifications.onMarkAllAsReadClick()

        assertFalse(viewModel.uiState.value.hasUnreadNotifications)
    }
}
