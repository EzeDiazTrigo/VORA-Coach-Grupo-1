package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppStat
import com.example.tp3_parcial_grupo_1.ui.components.AppStatDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun ReviewMealScreen(
    onBackClick: () -> Unit,
    onSaveMeal: (Meal) -> Unit
) {
    // El idioma se lee de la configuración para que Compose recomponga si cambia.
    val locale = LocalConfiguration.current.locales[0]
    val currentDate = remember(locale) { SimpleDateFormat("yyyy-MM-dd", locale).format(Date()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showDemoNotice: () -> Unit = { scope.launch { snackbarHostState.showSnackbar(Strings.DemoMode) } }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            AppTopBar(
                title = "Review Meal",
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.sm
            )

            Spacer(modifier = Modifier.height(16.dp))

            ReviewIngredientCard(name = "Grilled Chicken Breast", quantity = "180g", calories = "280 kcal", onDemoClick = showDemoNotice)
            Spacer(modifier = Modifier.height(12.dp))
            ReviewIngredientCard(name = "Basmati Rice", quantity = "150g", calories = "190 kcal", onDemoClick = showDemoNotice)
            Spacer(modifier = Modifier.height(12.dp))
            ReviewIngredientCard(name = "Mixed Steamed Vegetables", quantity = "100g", calories = "50 kcal", onDemoClick = showDemoNotice)

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = showDemoNotice) {
                Text(
                    text = "+ Add Ingredient",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Meal Totals Card (Dark Green)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MEAL TOTALS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "520 kcal",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppStat(
                            label = "PROTEIN",
                            value = "38g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.surface,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = "CARBS",
                            value = "54g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.surface,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = "FAT",
                            value = "14g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.surface,
                            spacing = 2.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AppButton(
                text = "Add to Lunch",
                onClick = {
                    val meal = Meal(
                        name = "Lunch",
                        date = currentDate,
                        type = MealType.LUNCH,
                        calories = 520,
                        protein = 38,
                        carbs = 54,
                        fat = 14,
                        itemsSummary = "Grilled Chicken - Basmati Rice - Vegetables"
                    )
                    onSaveMeal(meal)
                },
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "Save without logging",
                onClick = onBackClick,
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}

@Composable
fun ReviewIngredientCard(name: String, quantity: String, calories: String, onDemoClick: () -> Unit) {
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
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = calories,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = PillShape,
                        color = MaterialTheme.colorScheme.background,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            QuantityStepButton(symbol = "—", description = "Decrease quantity", onClick = onDemoClick)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = quantity, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(12.dp))
                            QuantityStepButton(symbol = "+", description = "Increase quantity", onClick = onDemoClick)
                        }
                    }
                }
                TextButton(onClick = onDemoClick) {
                    Text(
                        text = "Remove",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun QuantityStepButton(symbol: String, description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = description },
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}

