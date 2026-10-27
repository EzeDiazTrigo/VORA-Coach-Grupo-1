package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: AppIcons? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    uppercaseLabel: Boolean = false,
    labelAction: (@Composable () -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.small
) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxWidth()) {
        if (uppercaseLabel) {
            AppSectionLabel(label, modifier = Modifier.padding(bottom = MaterialTheme.spacing.xs))
        } else if (label.isNotBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.spacing.xs),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = label, style = MaterialTheme.typography.labelMedium, color = colors.onSurface)
                labelAction?.invoke()
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = placeholder?.let { { Text(it, color = colors.outline) } },
            leadingIcon = leadingIcon?.let { { AppIcon(it, contentDescription = null, size = MaterialTheme.iconSizes.xl, tint = colors.onSurfaceVariant) } },
            trailingIcon = trailingIcon,
            isError = errorMessage != null,
            supportingText = errorMessage?.let { { Text(it) } },
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.outlineVariant,
                errorBorderColor = colors.error
            )
        )
    }
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    errorMessage: String? = null,
    labelAction: (@Composable () -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.small
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = AppIcons.FieldLock,
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                // Figma solo exporta el ojo abierto: el tinte verde indica que la contraseña está visible.
                AppIcon(
                    AppIcons.FieldEye,
                    contentDescription = if (isVisible) Strings.HidePassword else Strings.ShowPassword,
                    size = MaterialTheme.iconSizes.xl,
                    tint = if (isVisible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        errorMessage = errorMessage,
        keyboardType = KeyboardType.Password,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        labelAction = labelAction,
        shape = shape
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            AppTextField(value = "alex.morgan@email.com", onValueChange = {}, label = "Email Address", leadingIcon = AppIcons.FieldMail)
            AppTextField(value = "", onValueChange = {}, label = "Full Name", placeholder = "Alex Morgan")
            AppTextField(value = "alex", onValueChange = {}, label = "Email Address", errorMessage = "Invalid email")
            PasswordTextField(value = "secret123", onValueChange = {}, label = "Password")
        }
    }
}
