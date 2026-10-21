package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NutritionDashboardScreen(
    uiState: MealUiState,
    onNavigate: (String) -> Unit,
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
            title = { Text("Delete Meal") },
            text = { Text("Are you sure you want to delete ${mealToDelete?.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    mealToDelete?.let { onDeleteMeal(it.id) }
                    mealToDelete = null
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mealToDelete = null }) {
                    Text("Cancel")
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
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
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
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Nutrition",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
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
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Calendar",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            item {
                // Calories Consumed Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CALORIES CONSUMED",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.extendedColors.textSubtle,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${uiState.totalCalories}",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 32.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Goal: ${uiState.goalCalories} kcal",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.extendedColors.textSubtle,
                                        fontSize = 12.sp
                                    )
                                )
                            }

                            val progressRatio = if (uiState.goalCalories > 0) (uiState.totalCalories.toFloat() / uiState.goalCalories).coerceIn(0f, 1f) else 0f
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(72.dp)
                            ) {
                                CircularProgressIndicator(
                                    progress = { progressRatio },
                                    modifier = Modifier.size(72.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.outlineVariant,
                                    strokeWidth = 6.dp,
                                )
                                Text(
                                    text = "${(progressRatio * 100).toInt()}%",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MacroIndicator(label = "Protein", current = "${uiState.totalProtein}g", total = "140g", progress = (uiState.totalProtein.toFloat() / 140f).coerceIn(0f, 1f), color = MaterialTheme.colorScheme.primary)
                            MacroIndicator(label = "Carbs", current = "${uiState.totalCarbs}g", total = "220g", progress = (uiState.totalCarbs.toFloat() / 220f).coerceIn(0f, 1f), color = MaterialTheme.colorScheme.secondary)
                            MacroIndicator(label = "Fat", current = "${uiState.totalFat}g", total = "65g", progress = (uiState.totalFat.toFloat() / 65f).coerceIn(0f, 1f), color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                }
            }

            item {
                // Advice Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.extendedColors.mintSoft)
                        .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sparkles),
                            contentDescription = "Advice",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "You're on track today. A protein-rich dinner would help you reach your target.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            item {
                // Today's Meals Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today’s Meals",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    TextButton(onClick = onNavigateToHistory) {
                        Text(
                            text = "View History",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
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
                // Tell Vora what you ate Card (Dark Green)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(20.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tell Vora what you ate",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Vora will automatically estimate the portions, ingredients, and log the nutrition details.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onNavigateToVoice,
                                modifier = Modifier.weight(1f),
                                shape = PillShape,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(text = "Voice", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                            Button(
                                onClick = onNavigateToPhoto,
                                modifier = Modifier.weight(1f),
                                shape = PillShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E4E45), contentColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Text(text = "Photo", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                            Button(
                                onClick = onNavigateToAddFood,
                                modifier = Modifier.weight(1f),
                                shape = PillShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E4E45), contentColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Text(text = "Search", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun MacroIndicator(label: String, current: String, total: String, progress: Float, color: Color) {
    Column(modifier = Modifier.width(96.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.extendedColors.textSubtle, fontSize = 11.sp))
            Text(text = "$current/$total", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 11.sp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(PillShape),
            color = color,
            trackColor = MaterialTheme.colorScheme.outlineVariant,
        )
    }
}

@Composable
fun MealRowCard(title: String, subtitle: String, calories: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.extendedColors.textSubtle,
                        fontSize = 12.sp
                    )
                )
            }
            Text(
                text = calories,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
fun DinnerCard(onAddDinner: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Dinner",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Not logged yet",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.extendedColors.textSubtle,
                        fontSize = 12.sp
                    )
                )
            }
            Button(
                onClick = onAddDinner,
                shape = PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.surface),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "+ Add",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
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
