package com.example.tp3_parcial_grupo_1.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppTextField
import com.example.tp3_parcial_grupo_1.ui.components.PasswordTextField
import com.example.tp3_parcial_grupo_1.ui.components.VoraBrandMarkStyle
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val PasswordHintIconSize = 14.dp

@Composable
fun RegisterRoute(
    onSignedUp: () -> Unit,
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSignedUp) {
        if (uiState.isSignedUp) {
            viewModel.onSignedUpHandled()
            onSignedUp()
        }
    }
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    RegisterScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onFullNameChange = viewModel::onFullNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignUpClick = viewModel::onSignUpClick,
        onBackClick = onBackClick,
        onSignInClick = onSignInClick,
        onDemoNoticeClick = viewModel::onDemoNoticeClick,
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    snackbarHostState: SnackbarHostState,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
    onDemoNoticeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        onBack = onBackClick,
        actions = { LanguagePill(onDemoNoticeClick, Modifier.padding(end = MaterialTheme.spacing.lg)) },
        snackbarHostState = snackbarHostState
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            AuthHeader(
                markStyle = VoraBrandMarkStyle.SignUp,
                titleStyle = MaterialTheme.typography.headlineLarge,
                taglineStyle = MaterialTheme.typography.labelSmall
            )
            AppCard {
                AppTextField(
                    value = uiState.fullName,
                    onValueChange = onFullNameChange,
                    label = AuthStrings.FullName,
                    placeholder = AuthStrings.FullNamePlaceholder,
                    leadingIcon = AppIcons.User,
                    errorMessage = uiState.fullNameError
                )
                AppTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = AuthStrings.Email,
                    placeholder = AuthStrings.EmailPlaceholder,
                    leadingIcon = AppIcons.FieldMail,
                    errorMessage = uiState.emailError,
                    keyboardType = KeyboardType.Email
                )
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = AuthStrings.Password,
                    placeholder = AuthStrings.PasswordPlaceholder,
                    errorMessage = uiState.passwordError
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
                    ) {
                        AppIcon(
                            AppIcons.CheckCircle,
                            contentDescription = null,
                            size = PasswordHintIconSize,
                            tint = if (uiState.hasSecurePassword) MaterialTheme.extendedColors.success else MaterialTheme.colorScheme.outline
                        )
                        Text(AuthStrings.SecureCharacters, style = MaterialTheme.typography.bodySmall)
                    }
                    AuthTextLink(AuthStrings.PasswordHelp, onDemoNoticeClick)
                }
                AppButton(
                    text = AuthStrings.SignUp,
                    onClick = onSignUpClick,
                    style = AppButtonStyle.Accent,
                    trailingIcon = AppIcons.ArrowRight
                )
                Text(
                    AuthStrings.OrSignUpWith,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                SocialButtonsRow(onClick = onDemoNoticeClick)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
            ) {
                BiometricNote()
                AuthFooterLink(AuthStrings.AccountIssues, AuthStrings.AccountIssuesLink, onDemoNoticeClick)
                AuthFooterLink(AuthStrings.HasAccount, AuthStrings.HasAccountLink, onSignInClick)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(
            uiState = RegisterUiState(fullName = "Alex Morgan", email = "alex.morgan@email.com", password = "12345678"),
            snackbarHostState = remember { SnackbarHostState() },
            onFullNameChange = {}, onEmailChange = {}, onPasswordChange = {}, onSignUpClick = {},
            onBackClick = {}, onSignInClick = {}, onDemoNoticeClick = {}
        )
    }
}
