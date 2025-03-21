package com.nomanr.lumo.multiplatform.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import com.nomanr.lumo.multiplatform.ui.configs.LocalAppConfigState
import com.nomanr.lumo.multiplatform.ui.configs.rememberAppConfigState
import com.nomanr.lumo.multiplatform.ui.foundation.ripple

object AppTheme {
    val colors: Colors
        @ReadOnlyComposable @Composable
        get() = LocalColors.current

    val typography: Typography
        @ReadOnlyComposable @Composable
        get() = LocalTypography.current

    val originalScaleTypography: Typography
        @ReadOnlyComposable @Composable
        get() = LocalOriginalTypography.current
}

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val rippleIndication = ripple()
    val selectionColors = rememberTextSelectionColors(LightColors)

    val appConfigState = rememberAppConfigState()
    val defaultTypography = provideTypography()
    val scaledTypography =  scaledTypography(appConfigState.fontScale, defaultTypography)
    val colors = if (isDarkTheme) appConfigState.colors.darkColors else appConfigState.colors.lightColors


    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDefaultColors provides AppColors(),
        LocalContentColor provides colors.contentColorFor(colors.background),
        LocalTypography provides scaledTypography,
        LocalOriginalTypography provides scaledTypography,
        LocalIndication provides rippleIndication,
        LocalTextSelectionColors provides selectionColors,
        LocalAppConfigState provides appConfigState,
        LocalTextStyle provides scaledTypography.body1,
        LocalLayoutDirection provides appConfigState.layoutDirection,
        content = content,
    )
}

@Composable
fun contentColorFor(color: Color): Color {
    return AppTheme.colors.contentColorFor(color)
}

@Composable
internal fun rememberTextSelectionColors(colorScheme: Colors): TextSelectionColors {
    val primaryColor = colorScheme.primary
    return remember(primaryColor) {
        TextSelectionColors(
            handleColor = primaryColor,
            backgroundColor = primaryColor.copy(alpha = TextSelectionBackgroundOpacity),
        )
    }
}

internal const val TextSelectionBackgroundOpacity = 0.4f
