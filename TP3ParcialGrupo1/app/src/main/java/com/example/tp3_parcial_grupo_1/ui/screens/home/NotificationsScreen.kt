package com.example.tp3_parcial_grupo_1.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import com.example.tp3_parcial_grupo_1.model.NotificationGroup
import com.example.tp3_parcial_grupo_1.model.NotificationItem
import com.example.tp3_parcial_grupo_1.model.NotificationType
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.HomeStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val TypeIconSize = 20.dp
private val UnreadDotSize = 6.dp

@Composable
fun NotificationsRoute(
    onNotificationOpen: (NotificationType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    NotificationsScreen(
        uiState = uiState,
        onMarkAllAsReadClick = viewModel::onMarkAllAsReadClick,
        onNotificationClick = { item ->
            viewModel.onNotificationClick(item.id)
            onNotificationOpen(item.type)
        },
        modifier = modifier
    )
}

@Composable
fun NotificationsScreen(
    uiState: NotificationsUiState,
    onMarkAllAsReadClick: () -> Unit,
    onNotificationClick: (NotificationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(modifier = modifier.fillMaxSize()) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = MaterialTheme.spacing.screen,
                vertical = MaterialTheme.spacing.lg
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            item(key = "header") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(HomeStrings.NotificationsTitle, style = MaterialTheme.typography.headlineLarge)
                    // Figma: 13 sp SemiBold en verde oscuro. Sin notificaciones sin leer no hay nada que marcar.
                    if (uiState.hasUnread) {
                        TextButton(onClick = onMarkAllAsReadClick) {
                            Text(HomeStrings.MarkAllAsRead, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
            NotificationGroup.entries.forEach { group ->
                val items = uiState.items.filter { it.group == group }
                if (items.isNotEmpty()) {
                    item(key = "group_${group.name}") {
                        Text(
                            if (group == NotificationGroup.Today) HomeStrings.GroupToday else HomeStrings.GroupYesterday,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    items(items, key = { it.id }) { item ->
                        NotificationCard(item, onClick = { onNotificationClick(item) })
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(item: NotificationItem, onClick: () -> Unit) {
    AppCard(onClick = onClick) {
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)) {
            AppIcon(
                item.type.icon(),
                contentDescription = null,
                size = TypeIconSize,
                tint = MaterialTheme.colorScheme.primary
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(verticalAlignment = Alignment.Top) {
                        Text(item.timeAgo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                        Box(modifier = Modifier.padding(start = MaterialTheme.spacing.xxs).size(UnreadDotSize)) {
                            if (item.isUnread) {
                                Box(Modifier.size(UnreadDotSize).background(MaterialTheme.colorScheme.primary, CircleShape))
                            }
                        }
                    }
                }
                Text(item.title, style = MaterialTheme.typography.labelLarge)
                Text(item.body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

private fun NotificationType.icon(): AppIcons = when (this) {
    NotificationType.Coach -> AppIcons.Sparkles
    NotificationType.Workout -> AppIcons.Dumbbell
    NotificationType.Nutrition -> AppIcons.Apple
    NotificationType.Milestone -> AppIcons.Flame
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
private fun NotificationsScreenPreview() {
    AppTheme {
        NotificationsScreen(
            uiState = NotificationsUiState(HomeDemoData.notifications),
            onMarkAllAsReadClick = {},
            onNotificationClick = {}
        )
    }
}
