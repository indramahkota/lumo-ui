package com.nomanr.sample.ui.components.otp_textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nomanr.sample.ui.AppTheme

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    state: OTPState = rememberOtpState(OTPTextFieldDefaults.OTPLength),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    autoFocus: Boolean = true,
    textStyle: TextStyle = AppTheme.typography.h3,
    colors: OTPTextFieldColors = OTPTextFieldDefaults.filledColors(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onComplete: (String) -> Unit = {},
) {
    OTPTextFieldComponent(
        modifier = modifier,
        state = state,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        autoFocus = autoFocus,
        textStyle = textStyle,
        type = OTPTextFieldType.Filled,
        colors = colors,
        visualTransformation = visualTransformation,
        onComplete = onComplete,
    )
}

@Composable
fun OutlinedOTPTextField(
    modifier: Modifier = Modifier,
    state: OTPState = rememberOtpState(OTPTextFieldDefaults.OTPLength),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    autoFocus: Boolean = true,
    textStyle: TextStyle = AppTheme.typography.h3,
    colors: OTPTextFieldColors = OTPTextFieldDefaults.outlinedColors(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onComplete: (String) -> Unit = {},
) {
    OTPTextFieldComponent(
        modifier = modifier,
        state = state,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        autoFocus = autoFocus,
        textStyle = textStyle,
        type = OTPTextFieldType.Outlined,
        colors = colors,
        visualTransformation = visualTransformation,
        onComplete = onComplete,
    )
}

@Composable
fun UnderlinedOTPTextField(
    modifier: Modifier = Modifier,
    state: OTPState = rememberOtpState(OTPTextFieldDefaults.OTPLength),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    autoFocus: Boolean = true,
    textStyle: TextStyle = AppTheme.typography.h3,
    colors: OTPTextFieldColors = OTPTextFieldDefaults.underlinedColors(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onComplete: (String) -> Unit = {},
) {
    OTPTextFieldComponent(
        modifier = modifier,
        state = state,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        autoFocus = autoFocus,
        textStyle = textStyle,
        colors = colors,
        visualTransformation = visualTransformation,
        onComplete = onComplete,
        type = OTPTextFieldType.Underlined
    )
}


@Composable
private fun OTPTextFieldComponent(
    modifier: Modifier = Modifier,
    state: OTPState,
    enabled: Boolean,
    readOnly: Boolean,
    isError: Boolean,
    autoFocus: Boolean = true,
    textStyle: TextStyle,
    type: OTPTextFieldType,
    colors: OTPTextFieldColors,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onComplete: (String) -> Unit,
) {

    LaunchedEffect(state.code) {
        if (state.isComplete()) {
            onComplete(state.code.trim())
        }
    }

    val otpLength = state.length
    val focusRequester = remember { FocusRequester() }

    CompositionLocalProvider(LocalTextSelectionColors provides colors.selectionColors) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(OTPTextFieldDefaults.ItemSpacing)
        ) {
            repeat(otpLength) { index ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    OTPTextFieldItem(
                        state = state,
                        position = index,
                        colors = colors,
                        textStyle = textStyle,
                        readOnly = readOnly,
                        enabled = enabled,
                        isError = isError,
                        focusRequester = focusRequester,
                        visualTransformation = visualTransformation,
                        type = type
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun OTPTextFieldItem(
    state: OTPState,
    position: Int,
    colors: OTPTextFieldColors,
    textStyle: TextStyle,
    readOnly: Boolean,
    enabled: Boolean,
    isError: Boolean,
    focusRequester: FocusRequester,
    visualTransformation: VisualTransformation,
    type: OTPTextFieldType
) {
    val singleValue = state.code[position]
    val value = if (singleValue.isWhitespace()) "" else singleValue.toString()

    val interactionSource = state.interactionSources[position]
    val cursorBrush = SolidColor(colors.cursorColor(isError).value)

    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled, isError, interactionSource).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor)).merge(TextStyle(textAlign = TextAlign.Center))

    BasicTextField(value = value,
        textStyle = mergedTextStyle,
        readOnly = readOnly,
        enabled = enabled,
        onValueChange = { newValue ->
            when {
                newValue.isNotEmpty() && newValue.last().isDigit() -> {
                    state.onDigitEntered(position, newValue.last())
                }

                newValue.isEmpty() -> {
                    state.onDigitDeleted(position)
                }
            }
        },
        modifier = Modifier
            .then(
                if (position == 0) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                }
            )
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace && state.isFieldEmpty(position)) {
                    state.onBackspacePressed(position)
                    true
                } else {
                    false
                }
            }
            .semantics {
                contentDescription = "OTP Digit ${position + 1} of ${state.length}"
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
        ),
        keyboardActions = KeyboardActions(onNext = {
            state.focusManager.moveFocus(FocusDirection.Next)
        }),
        singleLine = true,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = { inputField ->
            OTPTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = inputField,
                visualTransformation = visualTransformation,
                type = type,
                colors = colors,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
            )
        })

}

class OTPState(
    val length: Int,
    val focusManager: FocusManager,
    private val keyboardController: SoftwareKeyboardController?,
    initialOtp: String = ""
) {
    var code by mutableStateOf(initialOtp.padEnd(length, ' '))
        private set
    val interactionSources = List(length) { MutableInteractionSource() }

    fun onDigitEntered(index: Int, value: Char) {
        if (index in code.indices && value.isDigit()) {
            val chars = code.toCharArray()
            chars[index] = value
            code = String(chars)

            // Handle focus after digit entry
            if (index < length - 1) {
                focusManager.moveFocus(FocusDirection.Next)
            } else {
                keyboardController?.hide()
            }
        }
    }

    fun onDigitDeleted(index: Int) {
        if (index in code.indices) {
            val chars = code.toCharArray()
            chars[index] = ' '
            code = String(chars)

            if (index > 0 && isFieldEmpty(index)) {
                focusManager.moveFocus(FocusDirection.Previous)
            }
        }
    }

    fun onBackspacePressed(index: Int) {
        if (index > 0 && isFieldEmpty(index)) {
            focusManager.moveFocus(FocusDirection.Previous)
            onDigitDeleted(index - 1)
        }
    }

    fun isFieldEmpty(index: Int): Boolean {
        return code.getOrNull(index)?.isWhitespace() ?: true
    }

    fun isComplete(): Boolean {
        return code.trim().length == length
    }
}

@Composable
fun rememberOtpState(length: Int): OTPState {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    return remember(length, focusManager, keyboardController) {
        OTPState(
            length = length, focusManager = focusManager, keyboardController = keyboardController
        )
    }
}

@Preview
@Composable
fun PreviewOTPTextField() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OTPTextField(state = rememberOtpState(4), autoFocus = false, onComplete = { code ->
            println("OTP completed: $code")
        })

        OutlinedOTPTextField(state = rememberOtpState(4), autoFocus = true, onComplete = { code ->
            println("OTP completed: $code")
        })

        UnderlinedOTPTextField(state = rememberOtpState(4), autoFocus = false, onComplete = { code ->
            println("OTP completed: $code")
        })

        OTPTextField(state = rememberOtpState(6), autoFocus = false, onComplete = { code ->
            println("OTP completed: $code")
        })

        OutlinedOTPTextField(state = rememberOtpState(6), autoFocus = false, onComplete = { code ->
            println("OTP completed: $code")
        })

        UnderlinedOTPTextField(state = rememberOtpState(4), autoFocus = false, onComplete = { code ->
            println("OTP completed: $code")
        })

    }
}
