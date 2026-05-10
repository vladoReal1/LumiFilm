package com.example.lumifilm_semestralka.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import com.example.lumifilm_semestralka.ui.theme.DarkColorScheme

private val DarkColorScheme = darkColorScheme(
    primary = AppPrimary,           // zelená - hlavné tlačidlá
    onPrimary = AppOnPrimary,       // čierny text na zelených tlačidlách
    background = AppBackground,     // svetlošedé pozadie
    onBackground = AppDark,         // tmavý text na pozadí
    surface = AppSurface,           // biela pre karty
    onSurface = AppDark,            // tmavý text na kartách
    secondary = AppGold,            // zlatá pre hviezdy
    onSecondary = AppDark,
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = AppGrey
)

@Composable
fun MojaLumiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}