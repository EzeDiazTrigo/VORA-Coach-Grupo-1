package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors

enum class AppDestinationRoute(val route: String, val title: String, val icon: ImageVector) {
    HOME("home", "Home", Icons.Default.Home),
    WORKOUT("workout", "Workout", Icons.Default.Star),
    NUTRITION("nutrition", "Nutrition", Icons.Default.Refresh),
    PROGRESS("progress", "Progress", Icons.Default.Star),
    PROFILE("profile", "Profile", Icons.Default.Person)
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val destinations = listOf(
                AppDestinationRoute.HOME,
                AppDestinationRoute.WORKOUT,
                AppDestinationRoute.NUTRITION,
                AppDestinationRoute.PROGRESS,
                AppDestinationRoute.PROFILE
            )

            destinations.forEach { dest ->
                val isSelected = currentRoute == dest.route
                val tintColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.extendedColors.textSubtle
                val fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.SemiBold else androidx.compose.ui.text.font.FontWeight.Medium

                Column(
                    modifier = Modifier
                        .clickable { onNavigate(dest.route) }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = dest.icon,
                        contentDescription = dest.title,
                        tint = tintColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = dest.title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = tintColor,
                            fontSize = 11.sp,
                            fontWeight = fontWeight
                        )
                    )
                }
            }
        }
    }
}
