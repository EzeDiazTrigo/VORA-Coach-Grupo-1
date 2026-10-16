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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.HomeStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun NotificationsRoute(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    NotificationsScreen(
        uiState = uiState,
        onMarkAllAsReadClick = viewModel::onMarkAllAsReadClick,
        onNotificationClick = viewModel::onNotificationClick,
        modifier = modifier
    )
}

@Composable
fun NotificationsScreen(
    uiState: NotificationsUiState,
    onMarkAllAsReadClick: () -> Unit,
    onNotificationClick: (Int) -> Unit,
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
                    TextButton(onClick = onMarkAllAsReadClick, enabled = uiState.hasUnread) {
                        Text(HomeStrings.MarkAllAsRead, style = MaterialTheme.typography.labelMedium)
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
                        NotificationCard(item, onClick = { onNotificationClick(item.id) })
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
            Icon(
                imageVector = item.type.icon(),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
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
                        Box(modifier = Modifier.padding(start = MaterialTheme.spacing.xxs).size(6.dp)) {
                            if (item.isUnread) {
                                Box(Modifier.size(6.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
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

private fun NotificationType.icon(): ImageVector = when (this) {
    NotificationType.Coach -> Icons.Outlined.AutoAwesome
    NotificationType.Workout -> Icons.Outlined.FitnessCenter
    NotificationType.Nutrition -> Icons.Outlined.Restaurant
    NotificationType.Milestone -> Icons.Outlined.LocalFireDepartment
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
