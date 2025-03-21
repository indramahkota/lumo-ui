package com.nomanr.lumo.multiplatform.ui.components

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun windowContainerWidthInPx(): Int {
    val screen = UIScreen.mainScreen
    return (CGRectGetWidth(screen.bounds) * screen.scale).toInt()
}
