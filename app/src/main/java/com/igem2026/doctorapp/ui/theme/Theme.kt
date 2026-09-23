package com.igem2026.doctorapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppleBlue = Color(0xFF0066CC)
private val AppleFocusBlue = Color(0xFF0071E3)
private val SkyLinkBlue = Color(0xFF2997FF)
private val AppleInk = Color(0xFF1D1D1F)
private val AppleMuted = Color(0xFF6E6E73)
private val AppleWhite = Color(0xFFFFFFFF)
private val AppleParchment = Color(0xFFF5F5F7)
private val ApplePearl = Color(0xFFFAFAFC)
private val Hairline = Color(0xFFE0E0E0)
private val DividerSoft = Color(0xFFF0F0F0)
private val DarkTile = Color(0xFF272729)

private val LightColors = lightColorScheme(
    primary = AppleBlue,
    onPrimary = AppleWhite,
    primaryContainer = AppleParchment,
    onPrimaryContainer = AppleInk,
    inversePrimary = AppleFocusBlue,
    secondary = AppleBlue,
    onSecondary = AppleWhite,
    secondaryContainer = ApplePearl,
    onSecondaryContainer = AppleInk,
    tertiary = AppleBlue,
    onTertiary = AppleWhite,
    tertiaryContainer = ApplePearl,
    onTertiaryContainer = AppleInk,
    background = AppleWhite,
    onBackground = AppleInk,
    surface = AppleWhite,
    onSurface = AppleInk,
    surfaceVariant = AppleParchment,
    onSurfaceVariant = AppleMuted,
    surfaceTint = AppleBlue,
    inverseSurface = DarkTile,
    error = Color(0xFFD70015),
    onError = AppleWhite,
    errorContainer = Color(0xFFFFDADA),
    onErrorContainer = Color(0xFF3B0000),
    outline = Hairline,
    outlineVariant = DividerSoft,
    scrim = Color(0xFF000000),
)

private val AppleTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 56.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 60.sp,
        letterSpacing = (-0.5).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 40.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 44.sp,
        letterSpacing = (-0.5).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 34.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 50.sp,
        letterSpacing = (-0.37).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp,
        letterSpacing = (-0.3).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        letterSpacing = (-0.3).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 21.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 26.sp,
        letterSpacing = (-0.2).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        letterSpacing = (-0.2).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 22.sp,
        letterSpacing = (-0.37).sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = (-0.2).sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 25.sp,
        letterSpacing = (-0.37).sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp,
        letterSpacing = (-0.3).sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (-0.22).sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp,
        letterSpacing = (-0.22).sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 14.sp,
        letterSpacing = 0.sp,
    ),
)

private val AppleShapes = Shapes(
    extraSmall = RoundedCornerShape(5.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(11.dp),
    large = RoundedCornerShape(18.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

@Composable
fun DoctorAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppleTypography,
        shapes = AppleShapes,
        content = content,
    )
}