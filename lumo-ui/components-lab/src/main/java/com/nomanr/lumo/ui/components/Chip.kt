package com.nomanr.lumo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nomanr.lumo.ui.AppTheme
import com.nomanr.lumo.ui.components.ChipDefaults.ChipIconHorizontalPadding
import com.nomanr.lumo.ui.components.ChipDefaults.ChipIconSize
import com.nomanr.lumo.ui.components.ChipDefaults.ChipPillShape
import com.nomanr.lumo.ui.components.ChipDefaults.ChipRectShape
import com.nomanr.lumo.ui.foundation.ButtonElevation

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    type: ChipType = ChipType.Rect,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = ChipDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)
) {
    ChipComponent(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        style = ChipDefaults.primaryFilled(type.shape),
        onClick = onClick,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label
    )
}

@Composable
fun ElevatedChip(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    loading: Boolean = false,
    type: ChipType = ChipType.Rect,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = ChipDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)
) {
    ChipComponent(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        style = ChipDefaults.primaryElevated(type.shape),
        onClick = onClick,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label
    )
}

@Composable
fun OutlinedChip(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    loading: Boolean = false,
    type: ChipType = ChipType.Rect,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = ChipDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)
) {
    ChipComponent(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        style = ChipDefaults.primaryOutlined(type.shape),
        onClick = onClick,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label
    )
}

@Composable
fun GhostChip(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    loading: Boolean = false,
    type: ChipType = ChipType.Rect,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = ChipDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)
) {
    ChipComponent(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        style = ChipDefaults.primaryGhost(type.shape),
        onClick = onClick,
        contentPadding = contentPadding,
        interactionSource = interactionSource, leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label
    )
}

@Composable
private fun ChipComponent(
    modifier: Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    style: ChipStyle,
    onClick: () -> Unit,
    contentPadding: PaddingValues = ChipDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable () -> Unit,
) {

    val containerColor = style.colors.containerColor(enabled, selected).value
    val contentColor = style.colors.contentColor(enabled, selected).value
    val borderColor = style.colors.borderColor(enabled, selected).value
    val borderStroke = if (borderColor != null) BorderStroke(
        ChipDefaults.ChipOutlineHeight, borderColor
    ) else null

    val shadowElevation = style.elevation?.shadowElevation(enabled, interactionSource)?.value ?: 0.dp

    Surface(
        onClick = onClick,
        modifier = modifier
            .semantics { role = Role.Button },
        enabled = enabled,
        shape = style.shape,
        color = containerColor,
        contentColor = contentColor,
        border = borderStroke,
        shadowElevation = shadowElevation,
        interactionSource = interactionSource
    ) {

        DefaultChipComponent(
            modifier = Modifier.padding(contentPadding),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = label
        )
    }
}

@Composable
private fun DefaultChipComponent(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit),
) {

    Row(
        modifier, verticalAlignment = Alignment.CenterVertically
    ) {

        leadingIcon?.let { icon ->
            Box(
                modifier = Modifier
                    .padding(end = ChipIconHorizontalPadding)
                    .requiredSize(ChipIconSize)
            ) {
                icon.invoke()
            }
        }

        label.invoke()

        trailingIcon?.let { icon ->
            Box(
                modifier = Modifier
                    .padding(start = ChipIconHorizontalPadding)
                    .requiredSize(ChipIconSize)
            ) {
                icon.invoke()
            }
        }
    }
}

enum class ChipType(val shape: Shape) {
    Rect(ChipRectShape), Pill(ChipPillShape),
}

internal object ChipDefaults {
    val ChipPaddingHorizontal = 8.dp
    val ChipPaddingVertical = 8.dp
    val ChipRectShape = RoundedCornerShape(12)
    val ChipPillShape = RoundedCornerShape(50)
    val ChipOutlineHeight = 1.dp
    val ChipIconHorizontalPadding = 6.dp
    val ChipIconSize = 18.dp

    val contentPadding = PaddingValues(
        start = ChipPaddingHorizontal,
        end = ChipPaddingHorizontal,
        top = ChipPaddingVertical,
        bottom = ChipPaddingVertical
    )

    @Composable
    fun chipElevation() = ButtonElevation(
        defaultElevation = 4.dp,
        pressedElevation = 4.dp,
        focusedElevation = 4.dp,
        hoveredElevation = 4.dp,
        disabledElevation = 0.dp
    )

    @Composable
    fun primaryFilled(shape: Shape) = ChipStyle(
        colors = ChipColors(
            containerColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary,
            selectedContainerColor = AppTheme.colors.onPrimary,
            selectedContentColor = AppTheme.colors.primary,
            disabledContainerColor = AppTheme.colors.disabled,
            disabledContentColor = AppTheme.colors.onDisabled,
        ),
        shape = shape,
        elevation = null,
        contentPadding = contentPadding,
    )

    @Composable
    fun primaryElevated(shape: Shape) = ChipStyle(
        colors = ChipColors(
            containerColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary,
            selectedContainerColor = AppTheme.colors.onPrimary,
            selectedContentColor = AppTheme.colors.primary,
            disabledContainerColor = AppTheme.colors.disabled,
            disabledContentColor = AppTheme.colors.onDisabled,
        ),
        shape = shape,
        elevation = chipElevation(),
        contentPadding = contentPadding,
    )

    @Composable
    fun primaryOutlined(shape: Shape) = ChipStyle(
        colors = ChipColors(
            containerColor = AppTheme.colors.transparent,
            contentColor = AppTheme.colors.primary,
            outlineColor = AppTheme.colors.primary,
            selectedContainerColor = AppTheme.colors.primary,
            selectedOutlineColor = AppTheme.colors.primary,
            selectedContentColor = AppTheme.colors.onPrimary,
            disabledContainerColor = AppTheme.colors.transparent,
            disabledContentColor = AppTheme.colors.onDisabled,
            disabledOutlineColor = AppTheme.colors.disabled,
        ),
        shape = shape,
        elevation = null,
        contentPadding = contentPadding,
    )

    @Composable
    fun primaryGhost(shape: Shape) = ChipStyle(
        colors = ChipColors(
            containerColor = AppTheme.colors.transparent,
            contentColor = AppTheme.colors.primary,
            outlineColor = AppTheme.colors.transparent,
            selectedContainerColor = AppTheme.colors.primary,
            selectedContentColor = AppTheme.colors.onPrimary,
            selectedOutlineColor = AppTheme.colors.primary,
            disabledContainerColor = AppTheme.colors.transparent,
            disabledContentColor = AppTheme.colors.onDisabled,
            disabledOutlineColor = AppTheme.colors.transparent,
        ),
        shape = shape,
        elevation = null,
        contentPadding = contentPadding,
    )
}

@Immutable
internal data class ChipColors(
    val containerColor: Color,
    val contentColor: Color,
    val outlineColor: Color? = null,
    val selectedContainerColor: Color,
    val selectedOutlineColor: Color? = null,
    val selectedContentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val disabledOutlineColor: Color? = null,
) {
    @Composable
    internal fun containerColor(enabled: Boolean, selected: Boolean) = rememberUpdatedState(
        newValue = when {
            !enabled -> disabledContainerColor
            selected -> selectedContainerColor
            else -> containerColor
        }
    )

    @Composable
    internal fun contentColor(enabled: Boolean, selected: Boolean) = rememberUpdatedState(
        newValue = when {
            !enabled -> disabledContentColor
            selected -> selectedContentColor
            else -> contentColor
        }
    )

    @Composable
    fun borderColor(enabled: Boolean, selected: Boolean) = rememberUpdatedState(
        newValue = when {
            !enabled -> disabledOutlineColor
            selected -> selectedOutlineColor
            else -> outlineColor
        }
    )
}

@Immutable
internal data class ChipStyle(
    val colors: ChipColors,
    val shape: Shape,
    val elevation: ButtonElevation? = null,
    val contentPadding: PaddingValues,
)

@Composable
@Preview("Primary")
fun PrimaryChipPreview() {
    AppTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.background(Color.Red)) {
                    Chip {
                        Text("Hello")
                    }
                }

                Chip(enabled = false) {

                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                Chip(type = ChipType.Rect) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                Chip(enabled = false, type = ChipType.Rect) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ElevatedChip {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                ElevatedChip(enabled = false) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                ElevatedChip(type = ChipType.Rect) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }
                ElevatedChip(enabled = false, type = ChipType.Rect) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedChip {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                OutlinedChip(enabled = false) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                OutlinedChip(type = ChipType.Rect) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }

                OutlinedChip(enabled = false, type = ChipType.Rect) {
                    Icon(Icons.Filled.AcUnit, contentDescription = "Filled Icon Button")
                }
            }
        }
    }
}
