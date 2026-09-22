package com.igem2026.doctorapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF00696E),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF9CF0F5),
    onPrimaryContainer = Color(0xFF002021),
    secondary = Color(0xFF4A6365),
    tertiary = Color(0xFF4B5D8A),
    surface = Color(0xFFFAFDFC),
    background = Color(0xFFFAFDFC),
)

@Composable
fun DoctorAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content,
    )
}