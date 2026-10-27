package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppNoticeCard
import com.example.tp3_parcial_grupo_1.ui.components.AppPanelHeader
import com.example.tp3_parcial_grupo_1.ui.components.MacroProgressBar
import com.example.tp3_parcial_grupo_1.ui.components.ProgressRing
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NutritionDashboardScreen(
    uiState: MealUiState,
    onNavigateToAddFood: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToVoice: () -> Unit,
    onNavigateToPhoto: () -> Unit,
    onDeleteMeal: (Long) -> Unit,
    onDateSelected: (String) -> Unit
) {
    var mealToDelete by remember { mutableStateOf<Meal?>(null) }
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedCalendar.time)
                onDateSelected(dateStr)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    if (mealToDelete != null) {
        AlertDialog(
            onDismissRequest = { mealToDelete = null },
            title = { Text(NutritionStrings.title) },
            text = { Text(NutritionStrings.deleteMealConfirm(mealToDelete?.name)) },
            confirmButton = {
                TextButton(onClick = {
                    mealToDelete?.let { onDeleteMeal(it.id) }
                    mealToDelete = null
                }) {
                    Text(NutritionStrings.delete, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mealToDelete = null }) {
                    Text(NutritionStrings.cancel)
                }
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = formatDisplayDate(uiState.selectedDate),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = NutritionStrings.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    IconButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                    ) {
                        AppIcon(
                            AppIcons.Calendar,
                            contentDescription = NutritionStrings.calendar,
                            tint = MaterialTheme.colorScheme.onBackground,
                            size = 20.dp
                        )
                    }
                }
            }

            item {
                // Calories Consumed Card
                AppCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = NutritionStrings.caloriesConsumed,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.extendedColors.textSubtle,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))
                            Text(
                                text = "${uiState.totalCalories}",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${NutritionStrings.goal}: ${uiState.goalCalories} kcal",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.extendedColors.textSubtle
                                )
                            )
                        }

                        val progressRatio = if (uiState.goalCalories > 0) (uiState.totalCalories.toFloat() / uiState.goalCalories).coerceIn(0f, 1f) else 0f
                        ProgressRing(
                            progress = progressRatio,
                            size = 72.dp,
                            strokeWidth = 6.dp
                        ) {
                            Text(
                                text = "${(progressRatio * 100).toInt()}%",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MacroProgressBar(
                            stacked = true,
                            name = NutritionStrings.protein,
                            current = "${uiState.totalProtein}g",
                            goal = "140g",
                            progress = (uiState.totalProtein.toFloat() / 140f).coerceIn(0f, 1f),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f).padding(end = MaterialTheme.spacing.xs)
                        )
                        MacroProgressBar(
                            stacked = true,
                            name = NutritionStrings.carbs,
                            current = "${uiState.totalCarbs}g",
                            goal = "220g",
                            progress = (uiState.totalCarbs.toFloat() / 220f).coerceIn(0f, 1f),
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.weight(1f).padding(horizontal = MaterialTheme.spacing.xs)
                        )
                        MacroProgressBar(
                            stacked = true,
                            name = NutritionStrings.fat,
                            current = "${uiState.totalFat}g",
                            goal = "65g",
                            progress = (uiState.totalFat.toFloat() / 65f).coerceIn(0f, 1f),
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.weight(1f).padding(start = MaterialTheme.spacing.xs)
                        )
                    }
                }
            }

            item {
                // Advice Banner
                AppNoticeCard(
                    text = NutritionStrings.onTrackTip,
                    leading = {
                        AppIcon(AppIcons.Sparkles, contentDescription = null, size = MaterialTheme.iconSizes.xl, tint = MaterialTheme.colorScheme.primary)
                    }
                )
            }

            item {
                // Today's Meals Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = NutritionStrings.todaysMeals,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    TextButton(onClick = onNavigateToHistory) {
                        Text(
                            text = NutritionStrings.viewHistory,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            // Dynamic Meals List
            items(uiState.meals, key = { it.id }) { meal ->
                MealRowCard(
                    title = meal.name,
                    subtitle = meal.itemsSummary.ifBlank { meal.type.displayName() },
                    calories = "${meal.calories} kcal",
                    onClick = { onNavigateToDetail(meal.id) }
                )
            }

            if (uiState.meals.none { it.type == MealType.DINNER }) {
                item {
                    DinnerCard(onAddDinner = onNavigateToAddFood)
                }
            }

            item {
                // Tell Vora what you ate Card (Panel style)
                AppCard(style = AppCardStyle.Panel) {
                    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                        AppPanelHeader(
                            title = NutritionStrings.tellVoraTitle,
                            orb = { VoraLogo(size = 28.dp, glowAlpha = 0.3f) },
                            titleStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            titleColor = MaterialTheme.extendedColors.mint,
                            spacing = MaterialTheme.spacing.sm
                        )
                        Text(
                            text = NutritionStrings.tellVoraSubtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                AppButton(
                                    text = NutritionStrings.voice,
                                    onClick = onNavigateToVoice,
                                    style = AppButtonStyle.Accent,
                                    leadingIcon = AppIcons.Mic,
                                    isCompact = true,
                                    minHeight = AppButtonDefaults.SmallHeight,
                                    textStyle = MaterialTheme.typography.labelMedium
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                AppButton(
                                    text = NutritionStrings.photo,
                                    onClick = onNavigateToPhoto,
                                    style = AppButtonStyle.Primary,
                                    leadingIcon = AppIcons.Camera,
                                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f),
                                    textColor = MaterialTheme.colorScheme.onPrimary,
                                    isCompact = true,
                                    minHeight = AppButtonDefaults.SmallHeight,
                                    textStyle = MaterialTheme.typography.labelMedium
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                AppButton(
                                    text = NutritionStrings.search,
                                    onClick = onNavigateToAddFood,
                                    style = AppButtonStyle.Primary,
                                    leadingIcon = AppIcons.Search,
                                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f),
                                    textColor = MaterialTheme.colorScheme.onPrimary,
                                    isCompact = true,
                                    minHeight = AppButtonDefaults.SmallHeight,
                                    textStyle = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
            }
        }
    }
}

@Composable
fun MealRowCard(title: String, subtitle: String, calories: String, onClick: () -> Unit) {
    AppCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.extendedColors.textSubtle
                    )
                )
            }
            Text(
                text = calories,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun DinnerCard(onAddDinner: () -> Unit) {
    AppCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = NutritionStrings.dinner,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = NutritionStrings.notLoggedYet,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.extendedColors.textSubtle
                    )
                )
            }
            Box(modifier = Modifier.width(100.dp)) {
                AppButton(
                    text = NutritionStrings.add,
                    onClick = onAddDinner,
                    style = AppButtonStyle.Primary,
                    isCompact = true
                )
            }
        }
    }
}

fun formatDisplayDate(dateStr: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(dateStr)
        if (date != null) {
            SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(date).uppercase()
        } else {
            dateStr
        }
    } catch (_: Exception) {
        dateStr
    }
}
