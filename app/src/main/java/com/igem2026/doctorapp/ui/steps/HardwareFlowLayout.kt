package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.LensPreviewWindow
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.RadioChip
import kotlinx.coroutines.delay

@Composable
fun HardwareFlowLayout(
    lensConnected: Boolean,
    actionBar: @Composable () -> Unit,
) {
    var manualMode by remember { mutableStateOf(true) }
    var manualSeconds by remember { mutableStateOf("") }
    var presetIndex by remember { mutableIntStateOf(0) }
    var running by remember { mutableStateOf(false) }
    var elapsed by remember { mutableLongStateOf(0L) }

    val presets = listOf("<预制方案A>", "<预制方案B>", "<预制方案C>")
    val totalSeconds = if (manualMode) {
        manualSeconds.toIntOrNull() ?: 0
    } else {
        (presetIndex + 1) * 60
    }
    val remainingSeconds = kotlin.math.max(totalSeconds.toLong() - elapsed, 0L)
    val progress = if (totalSeconds > 0) {
        remainingSeconds.toFloat() / totalSeconds
    } else {
        0f
    }
    val displayText = if (manualMode && manualSeconds.isBlank()) {
        "--:--"
    } else {
        "%02d:%02d".format(remainingSeconds / 60, remainingSeconds % 60)
    }

    LaunchedEffect(running) {
        if (running) {
            while (running && elapsed < totalSeconds) {
                delay(1000)
                elapsed += 1
            }
            running = elapsed >= totalSeconds
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            contentAlignment = Alignment.Center,
        ) {
            actionBar()
        }

        LensPreviewWindow(
            connected = lensConnected,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "计时器",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier.size(84.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 5.dp,
                    )
                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        RadioChip(label = "手动", selected = manualMode, onClick = { manualMode = true })
                        RadioChip(label = "预制", selected = !manualMode, onClick = { manualMode = false })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Button(
                            onClick = {
                                if (running) running = false else running = true
                            },
                            modifier = Modifier.weight(1f),
                            enabled = running || totalSeconds > 0,
                        ) {
                            Text(if (running) "暂停" else "开始倒计时")
                        }
                        OutlinedButton(
                            onClick = {
                                running = false
                                elapsed = 0L
                            },
                            enabled = elapsed > 0L || running,
                        ) {
                            Text("重置")
                        }
                    }
                }
            }

            if (manualMode) {
                OutlinedTextField(
                    value = manualSeconds,
                    onValueChange = { manualSeconds = it.filter { c -> c.isDigit() } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    label = { Text("手动倒计时时长（秒）") },
                    placeholder = { PlaceholderInline("例如 120") },
                    singleLine = true,
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    presets.forEachIndexed { i, label ->
                        RadioChip(label = label, selected = presetIndex == i, onClick = { presetIndex = i })
                    }
                }
            }
        }
    }
}

@Composable
fun MiniToggle(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.scale(0.85f),
        )
    }
}