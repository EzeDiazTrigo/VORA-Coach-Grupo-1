package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

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
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppTag
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
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
                .padding(20.dp)
        ) {
            AppTopBar(
                title = "Analyse Meal",
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.sm
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Photo Preview Box with bounding tags
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_meal_demo),
                    contentDescription = "Meal Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 32.dp, start = 24.dp)
                        .clip(PillShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "Grilled Chicken (~180g)", color = MaterialTheme.colorScheme.surface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 48.dp, end = 24.dp)
                        .clip(PillShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "Basmati Rice (~150g)", color = MaterialTheme.colorScheme.surface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // VORA Estimate Card
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
                            text = "VORA ESTIMATE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Portions are estimated",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "520 kcal",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AppTag(
                                text = "P: 38g",
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            )
                            AppTag(
                                text = "C: 54g",
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            )
                            AppTag(
                                text = "F: 14g",
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "\"Portions and quantities can be refined in the next step before saving.\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.extendedColors.textSubtle,
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppButton(
                text = "Review & Add",
                onClick = onReviewAndAdd,
                style = AppButtonStyle.Primary,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "Retake Photo",
                onClick = onBackClick,
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}

