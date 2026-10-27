package com.example.tp3_parcial_grupo_1.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppTextField
import com.example.tp3_parcial_grupo_1.ui.components.LabeledDivider
import com.example.tp3_parcial_grupo_1.ui.components.PasswordTextField
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun LoginRoute(
    onSignedIn: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSignedIn) {
        if (uiState.isSignedIn) {
            viewModel.onSignedInHandled()
            onSignedIn()
        }
    }
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    LoginScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = viewModel::onSignInClick,
        onSignUpClick = onSignUpClick,
        onDemoNoticeClick = viewModel::onDemoNoticeClick,
        onEnterDemoClick = onSignedIn,
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    snackbarHostState: SnackbarHostState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onDemoNoticeClick: () -> Unit,
    onEnterDemoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Login es la pantalla inicial: no hay a dónde volver, así que la barra solo muestra el idioma.
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHostState = snackbarHostState,
        showTopBar = true,
        actions = { LanguagePill(onDemoNoticeClick, Modifier.padding(end = MaterialTheme.spacing.lg)) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.xl),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            AuthHeader(subtitle = AuthStrings.Subtitle)
            AppCard {
                AppTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = AuthStrings.Email,
                    placeholder = AuthStrings.EmailPlaceholder,
                    leadingIcon = AppIcons.FieldMail,
                    errorMessage = uiState.emailError,
                    keyboardType = KeyboardType.Email,
                    shape = MaterialTheme.shapes.medium
                )
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = AuthStrings.Password,
                    placeholder = AuthStrings.PasswordPlaceholder,
                    errorMessage = uiState.passwordError,
                    labelAction = { ForgotPasswordLink(onDemoNoticeClick) },
                    shape = MaterialTheme.shapes.medium
                )
                AuthSubmitButton(AuthStrings.SignIn, onSignInClick)
                LabeledDivider(AuthStrings.OrContinueWith)
                SocialButtonsRow(onClick = onDemoNoticeClick)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
            ) {
                BiometricNote()
                AuthFooterLink(AuthStrings.NoAccount, AuthStrings.NoAccountLink, onSignUpClick)
                AppButton(
                    text = AuthStrings.EnterDemoMode,
                    onClick = onEnterDemoClick,
                    style = AppButtonStyle.Outlined
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            uiState = LoginUiState(),
            snackbarHostState = remember { SnackbarHostState() },
            onEmailChange = {}, onPasswordChange = {}, onSignInClick = {},
            onSignUpClick = {}, onDemoNoticeClick = {}, onEnterDemoClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun LoginScreenErrorPreview() {
    AppTheme {
        LoginScreen(
            uiState = LoginUiState(email = "alex", emailError = AuthStrings.EmailInvalid, passwordError = AuthStrings.PasswordRequired),
            snackbarHostState = remember { SnackbarHostState() },
            onEmailChange = {}, onPasswordChange = {}, onSignInClick = {},
            onSignUpClick = {}, onDemoNoticeClick = {}, onEnterDemoClick = {}
        )
    }
}
