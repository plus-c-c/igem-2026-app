package com.igem2026.doctorapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

enum class DoctorModule { HOME, PRE_ORDER, APPLICATION }

@Composable
fun DoctorAppRoot() {
    var moduleOrdinal by rememberSaveable { mutableIntStateOf(DoctorModule.HOME.ordinal) }
    var languageOrdinal by rememberSaveable { mutableIntStateOf(AppLanguage.ENGLISH.ordinal) }
    val module = DoctorModule.entries.getOrNull(moduleOrdinal) ?: DoctorModule.HOME
    val language = AppLanguage.entries.getOrNull(languageOrdinal) ?: AppLanguage.ENGLISH

    if (module != DoctorModule.HOME) {
        BackHandler { moduleOrdinal = DoctorModule.HOME.ordinal }
    }

    CompositionLocalProvider(LocalAppLanguage provides language) {
        when (module) {
            DoctorModule.HOME -> HomeScreen(
                onToggleLanguage = null,
                onOpenPreOrder = { moduleOrdinal = DoctorModule.PRE_ORDER.ordinal },
                onOpenApplication = { moduleOrdinal = DoctorModule.APPLICATION.ordinal },
            )

            DoctorModule.PRE_ORDER -> PreOrderScreen(
                onBack = { moduleOrdinal = DoctorModule.HOME.ordinal },
                onOrderPlaced = { moduleOrdinal = DoctorModule.APPLICATION.ordinal },
            )

            DoctorModule.APPLICATION -> ApplicationScreen(
                onBack = { moduleOrdinal = DoctorModule.HOME.ordinal },
            )
        }
    }
}
