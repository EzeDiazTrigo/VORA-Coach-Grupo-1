package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import com.example.tp3_parcial_grupo_1.data.demo.AnalysedMeal
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppTag
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.chip
import com.example.tp3_parcial_grupo_1.ui.theme.micro
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun AnalyseMealPhotoScreen(
    onBackClick: () -> Unit,
    onReviewAndAdd: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.screen)
        ) {
            AppTopBar(
                title = NutritionStrings.analyseMeal,
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.sm
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Photo Preview Box with bounding tags
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_meal_demo),
                    contentDescription = NutritionStrings.mealPhoto,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = MaterialTheme.spacing.xxl, start = MaterialTheme.spacing.xl)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = MaterialTheme.spacing.xxs)
                ) {
                    Text(text = NutritionStrings.photoTagChicken, color = MaterialTheme.colorScheme.surface, style = MaterialTheme.typography.labelSmall)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 48.dp, end = MaterialTheme.spacing.xl)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = MaterialTheme.spacing.xxs)
                ) {
                    Text(text = NutritionStrings.photoTagRice, color = MaterialTheme.colorScheme.surface, style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // VORA Estimate Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(MaterialTheme.spacing.lg)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NutritionStrings.voraEstimate,
                            style = MaterialTheme.typography.micro.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = NutritionStrings.portionsEstimated,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle
)
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NutritionStrings.caloriesValue(AnalysedMeal.calories),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
                            AppTag(
                                text = NutritionStrings.macroChip("P", AnalysedMeal.protein),
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = MaterialTheme.spacing.xs),
                                shape = RoundedCornerShape(4.dp)
                            )
                            AppTag(
                                text = NutritionStrings.macroChip("C", AnalysedMeal.carbs),
                                containerColor = MaterialTheme.extendedColors.mintSoft,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = MaterialTheme.spacing.xs),
                                shape = RoundedCornerShape(4.dp)
                            )
                            AppTag(
                                text = NutritionStrings.macroChip("F", AnalysedMeal.fat),
                                containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f),
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = MaterialTheme.spacing.xs),
                                shape = RoundedCornerShape(4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            Text(
                text = NutritionStrings.refineNote,
                style = MaterialTheme.typography.chip.copy(
                    color = MaterialTheme.extendedColors.textSubtle
)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            AppButton(
                text = NutritionStrings.reviewAndAdd,
                onClick = onReviewAndAdd,
                style = AppButtonStyle.Primary,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            AppButton(
                text = NutritionStrings.retakePhoto,
                onClick = onBackClick,
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}

