package com.example.tp3_parcial_grupo_1.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.strings.ComponentStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Pestañas de la barra inferior de Figma. La navegación (rutas, back stack) la resuelve AppNavHost.
enum class BottomNavItem(val label: String, @DrawableRes val icon: Int) {
    Home(ComponentStrings.NavHome, R.drawable.ic_home),
    Workout(ComponentStrings.NavWorkout, R.drawable.ic_dumbbell),
    Nutrition(ComponentStrings.NavNutrition, R.drawable.ic_apple),
    Progress(ComponentStrings.NavProgress, R.drawable.ic_chart_line),
    Profile(ComponentStrings.NavProfile, R.drawable.ic_user)
}

@Composable
fun BottomNavBar(
    selected: BottomNavItem,
    onSelect: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surface) {
        Column(modifier = Modifier.navigationBarsPadding()) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .padding(horizontal = MaterialTheme.spacing.lg)
                    .selectableGroup(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem.entries.forEach { item ->
                    BottomNavTab(
                        item = item,
                        isSelected = item == selected,
                        onClick = { if (item != selected) onSelect(item) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavTab(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.extendedColors.textSubtle
    Column(
        modifier = modifier
            .selectable(selected = isSelected, onClick = onClick, role = Role.Tab)
            .padding(vertical = MaterialTheme.spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
    ) {
        Icon(
            painter = painterResource(item.icon),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun BottomNavBarPreview() {
    AppTheme {
        BottomNavBar(selected = BottomNavItem.Home, onSelect = {})
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun BottomNavBarNarrowPreview() {
    AppTheme {
        BottomNavBar(selected = BottomNavItem.Nutrition, onSelect = {})
    }
}
