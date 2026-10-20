package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.ui.components.BottomNavBar
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors

@Composable
fun TodaysMealsScreen(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddDinner: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(currentRoute = "nutrition", onNavigate = onNavigate)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                    }
                    Column {
                        Text(
                            text = "TIMELINE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "Today’s Meals",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                    }
                }
                Surface(
                    shape = PillShape,
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Text(
                        text = "1,420 kcal",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Breakfast Section
            DetailedMealSection(
                mealTitle = "Breakfast",
                totalCalories = "420 kcal",
                items = listOf(
                    Triple("Greek Yoghurt (0% Fat)", "200g", "150 kcal"),
                    Triple("Fresh Blueberries", "75g", "60 kcal"),
                    Triple("Organic Honey Granola", "50g", "210 kcal")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lunch Section
            DetailedMealSection(
                mealTitle = "Lunch",
                totalCalories = "520 kcal",
                items = listOf(
                    Triple("Grilled Chicken Breast", "180g", "280 kcal"),
                    Triple("Basmati Rice", "150g", "190 kcal"),
                    Triple("Mixed Steamed Vegetables", "100g", "50 kcal")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Snack Section
            DetailedMealSection(
                mealTitle = "Snack",
                totalCalories = "240 kcal",
                items = listOf(
                    Triple("Whey Protein Shake", "350ml (water)", "240 kcal")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dinner Section (Not logged)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Dinner is not logged yet",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.extendedColors.textSubtle,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onAddDinner,
                        shape = PillShape,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(text = "+ Add Dinner", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailedMealSection(mealTitle: String, totalCalories: String, items: List<Triple<String, String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
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
                    text = mealTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = totalCalories,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = item.first,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        )
                        Text(
                            text = item.second,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Text(
                        text = item.third,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.extendedColors.textSubtle,
                            fontSize = 13.sp
                        )
                    )
                }
                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}
