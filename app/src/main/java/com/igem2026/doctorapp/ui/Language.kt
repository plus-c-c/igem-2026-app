package com.igem2026.doctorapp.ui

import androidx.compose.runtime.staticCompositionLocalOf

enum class AppLanguage { CHINESE, ENGLISH }

val LocalAppLanguage = staticCompositionLocalOf { AppLanguage.ENGLISH }

