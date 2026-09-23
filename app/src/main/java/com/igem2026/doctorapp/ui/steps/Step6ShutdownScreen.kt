package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Step6ShutdownScreen() {
    var magneticOn by remember { mutableStateOf(false) }
    var blueLightOn by remember { mutableStateOf(false) }

    val anyOn = magneticOn || blueLightOn

    HardwareFlowLayout(
        lensConnected = anyOn,
        actionBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    MiniToggle(label = "磁场", checked = magneticOn) { magneticOn = it }
                    MiniToggle(label = "蓝光", checked = blueLightOn) { blueLightOn = it }
                }
                Button(
                    onClick = {
                        magneticOn = false
                        blueLightOn = false
                    },
                    enabled = anyOn,
                ) {
                    Text("一键关闭")
                }
            }
        },
    )
}