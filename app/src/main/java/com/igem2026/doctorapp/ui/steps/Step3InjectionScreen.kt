package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Step3InjectionScreen() {
    var lensConnected by remember { mutableStateOf(false) }
    var injected by remember { mutableStateOf(false) }

    HardwareFlowLayout(
        lensConnected = lensConnected,
        actionBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MiniToggle(label = "连接镜头", checked = lensConnected) { lensConnected = it }
                MiniToggle(label = "注射", checked = injected) { injected = it }
            }
        },
    )
}