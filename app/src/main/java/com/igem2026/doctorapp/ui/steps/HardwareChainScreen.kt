package com.igem2026.doctorapp.ui.steps

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.AppLanguage
import com.igem2026.doctorapp.ui.DoctorStep
import com.igem2026.doctorapp.ui.LocalAppLanguage
import com.igem2026.doctorapp.ui.components.LensPreviewWindow
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.RadioChip
import kotlinx.coroutines.delay

@Composable
fun HardwareChainScreen(step: DoctorStep) {
    var lensConnected by rememberSaveable { mutableStateOf(false) }

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
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    fadeIn(tween(180)) togetherWith fadeOut(tween(120))
                },
                label = "hardware-action",
            ) { currentStep ->
                HardwareActionRow(
                    step = currentStep,
                    lensConnected = lensConnected,
                    onLensConnectedChange = { lensConnected = it },
                )
            }
        }

        LensPreviewWindow(
            connected = lensConnected,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true),
        )

        HardwareTimer()
    }
}

@Composable
private fun HardwareActionRow(
    step: DoctorStep,
    lensConnected: Boolean,
    onLensConnectedChange: (Boolean) -> Unit,
) {
    when (step) {
        DoctorStep.INJECTION -> InjectionRow(
            lensConnected = lensConnected,
            onLensConnectedChange = onLensConnectedChange,
        )
        DoctorStep.MAGNETIC -> MagneticRow()
        DoctorStep.BLUELIGHT -> BlueLightRow()
        DoctorStep.SHUTDOWN -> ShutdownRow()
        else -> Unit
    }
}

@Composable
private fun InjectionRow(
    lensConnected: Boolean,
    onLensConnectedChange: (Boolean) -> Unit,
) {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    var injected by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MiniToggle(label = if (english) "Connect Camera" else "连接镜头", checked = lensConnected, onCheckedChange = onLensConnectedChange)
        MiniToggle(label = if (english) "Inject" else "注射", checked = injected) { injected = it }
    }
}

@Composable
private fun MagneticRow() {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    var magneticOn by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (english) {
                if (magneticOn) "Magnetic Field Enabled" else "Magnetic Field Disabled"
            } else {
                if (magneticOn) "磁场已开启" else "磁场未开启"
            },
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = if (magneticOn) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            },
            modifier = Modifier.weight(1f),
        )
        Button(
            onClick = { magneticOn = true },
            enabled = !magneticOn,
        ) {
            Text(if (english) "Enable Magnetic Field" else "打开磁场")
        }
    }
}

@Composable
private fun BlueLightRow() {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    var blueLightOn by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (english) {
                if (blueLightOn) "Blue Light Enabled" else "Blue Light Disabled"
            } else {
                if (blueLightOn) "蓝光已开启" else "蓝光未开启"
            },
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = if (blueLightOn) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            },
            modifier = Modifier.weight(1f),
        )
        Button(
            onClick = { blueLightOn = true },
            enabled = !blueLightOn,
        ) {
            Text(if (english) "Enable Blue Light" else "打开蓝光")
        }
    }
}

@Composable
private fun ShutdownRow() {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    var magneticOn by remember { mutableStateOf(false) }
    var blueLightOn by remember { mutableStateOf(false) }
    val anyOn = magneticOn || blueLightOn

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MiniToggle(label = if (english) "Magnetic Field" else "磁场", checked = magneticOn) { magneticOn = it }
            MiniToggle(label = if (english) "Blue Light" else "蓝光", checked = blueLightOn) { blueLightOn = it }
        }
        Button(
            onClick = {
                magneticOn = false
                blueLightOn = false
            },
            enabled = anyOn,
        ) {
            Text(if (english) "Disable Both" else "一键关闭")
        }
    }
}

@Composable
private fun HardwareTimer() {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    var manualMode by remember { mutableStateOf(true) }
    var manualSeconds by remember { mutableStateOf("") }
    var presetIndex by remember { mutableIntStateOf(0) }
    var running by remember { mutableStateOf(false) }
    var elapsed by remember { mutableLongStateOf(0L) }

    val presets = if (english) {
        listOf("Preset A", "Preset B", "Preset C")
    } else {
        listOf("预制方案 A", "预制方案 B", "预制方案 C")
    }
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
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = if (english) "Timer" else "计时器",
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
                    RadioChip(label = if (english) "Manual" else "手动", selected = manualMode, onClick = { manualMode = true })
                    RadioChip(label = if (english) "Preset" else "预制", selected = !manualMode, onClick = { manualMode = false })
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
                        Text(if (english) {
                            if (running) "Pause" else "Start Countdown"
                        } else {
                            if (running) "暂停" else "开始倒计时"
                        })
                    }
                    OutlinedButton(
                        onClick = {
                            running = false
                            elapsed = 0L
                        },
                        enabled = elapsed > 0L || running,
                    ) {
                        Text(if (english) "Reset" else "重置")
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
                label = { Text(if (english) "Manual countdown duration (seconds)" else "手动倒计时时长（秒）") },
                placeholder = { PlaceholderInline(if (english) "e.g. 120" else "例如 120") },
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
