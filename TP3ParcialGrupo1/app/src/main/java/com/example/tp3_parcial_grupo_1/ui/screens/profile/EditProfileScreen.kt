package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppDropdownField
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppTextField
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val PhotoSize = 80.dp
private val HintIconSize = 16.dp

@Composable
fun EditProfileRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onBackClick()
    }

    EditProfileScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onChangePhotoClick = viewModel::onChangePhotoClick,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onDateOfBirthChange = viewModel::onDateOfBirthChange,
        onHeightChange = viewModel::onHeightChange,
        onWeightChange = viewModel::onWeightChange,
        onGenderChange = viewModel::onGenderChange,
        onSaveClick = viewModel::onSaveClick,
        modifier = modifier
    )
}

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onChangePhotoClick: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onDateOfBirthChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        title = ProfileStrings.EditProfileTitle,
        onBack = onBackClick,
        snackbarHostState = snackbarHostState
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            PhotoSection(onChangePhotoClick = onChangePhotoClick)
            AppTextField(
                value = uiState.firstName,
                onValueChange = onFirstNameChange,
                label = ProfileStrings.FirstName,
                errorMessage = uiState.firstNameError
            )
            AppTextField(
                value = uiState.lastName,
                onValueChange = onLastNameChange,
                label = ProfileStrings.LastName,
                errorMessage = uiState.lastNameError
            )
            AppTextField(
                value = uiState.dateOfBirth,
                onValueChange = onDateOfBirthChange,
                label = ProfileStrings.DateOfBirth,
                errorMessage = uiState.dateOfBirthError
            )
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                AppTextField(
                    value = uiState.height,
                    onValueChange = onHeightChange,
                    label = ProfileStrings.Height,
                    modifier = Modifier.weight(1f),
                    errorMessage = uiState.heightError,
                    keyboardType = KeyboardType.Number,
                    trailingIcon = { UnitLabel(ProfileStrings.UnitCm) }
                )
                AppTextField(
                    value = uiState.weight,
                    onValueChange = onWeightChange,
                    label = ProfileStrings.Weight,
                    modifier = Modifier.weight(1f),
                    errorMessage = uiState.weightError,
                    keyboardType = KeyboardType.Number,
                    trailingIcon = { UnitLabel(ProfileStrings.UnitKg) }
                )
            }
            GenderField(selected = uiState.gender, onSelected = onGenderChange)
            AppButton(text = ProfileStrings.SaveChanges, onClick = onSaveClick)
            Text(
                ProfileStrings.EditProfileFooter,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.extendedColors.textSubtle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PhotoSection(onChangePhotoClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
    ) {
        Image(
            painter = painterResource(R.drawable.img_avatar_demo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(PhotoSize).clip(CircleShape)
        )
        TextButton(onClick = onChangePhotoClick) {
            Text(ProfileStrings.ChangePhoto, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun UnitLabel(unit: String) {
    Text(
        unit,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(end = MaterialTheme.spacing.md)
    )
}

@Composable
private fun GenderField(selected: String, onSelected: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)) {
        AppDropdownField(
            label = ProfileStrings.Gender,
            selected = selected,
            options = ProfileStrings.GenderOptions,
            onSelected = onSelected
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.extendedColors.mintSoft, MaterialTheme.shapes.small)
                .padding(MaterialTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
        ) {
            Icon(
                painterResource(R.drawable.ic_info),
                contentDescription = null,
                modifier = Modifier.size(HintIconSize),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                ProfileStrings.GenderHint,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 900)
@Composable
private fun EditProfileScreenPreview() {
    AppTheme {
        EditProfileScreen(
            uiState = EditProfileUiState(
                firstName = "Alex", lastName = "Morgan", dateOfBirth = "14 March 1995",
                height = "178", weight = "76", gender = "Prefer not to say"
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {}, onChangePhotoClick = {}, onFirstNameChange = {}, onLastNameChange = {},
            onDateOfBirthChange = {}, onHeightChange = {}, onWeightChange = {}, onGenderChange = {},
            onSaveClick = {}
        )
    }
}
