package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.BlueLightSchematic
import com.igem2026.doctorapp.ui.components.HardwareSchematicCard
import com.igem2026.doctorapp.ui.components.LensPreviewWindow
import com.igem2026.doctorapp.ui.components.MagneticFieldSchematic
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.RadioChip
import com.igem2026.doctorapp.ui.components.SectionTitle

@Composable
fun Step4HardwareScreen() {
    var injection by remember { mutableStateOf(false) }
    var lensConnected by remember { mutableStateOf(false) }
    var magneticField by remember { mutableStateOf(false) }
    var blueLight by remember { mutableStateOf(false) }
    var manualMode by remember { mutableStateOf(true) }
    var manualSeconds by remember { mutableStateOf("") }
    var presetIndex by remember { mutableIntStateOf(0) }

    val presets = listOf("<预制方案A>", "<预制方案B>", "<预制方案C>")
    val totalSeconds = if (manualMode) {
        manualSeconds.toIntOrNull() ?: 0
    } else {
        (presetIndex + 1) * 60
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("注射与硬件控制", style = MaterialTheme.typography.titleLarge)

        SectionTitle("硬件开关")
        ToggleRow("注射（加载样本/试剂）", injection) { injection = it }
        ToggleRow("连接硬件镜头", lensConnected) { lensConnected = it }

        LensPreviewWindow(
            connected = lensConnected,
            modifier = Modifier.fillMaxWidth(),
        )

        ToggleRow("打开磁场", magneticField) { magneticField = it }
        ToggleRow("蓝光开关", blueLight) { blueLight = it }

        HardwareSchematicCard(
            title = "磁场示意图",
            enabled = magneticField,
            modifier = Modifier.fillMaxWidth(),
        ) { diagramModifier ->
            MagneticFieldSchematic(enabled = magneticField, modifier = diagramModifier)
        }

        HardwareSchematicCard(
            title = "蓝光示意图",
            enabled = blueLight,
            modifier = Modifier.fillMaxWidth(),
        ) { diagramModifier ->
            BlueLightSchematic(enabled = blueLight, modifier = diagramModifier)
        }

        SectionTitle("倒计时 · 模式选择")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RadioChip(
                label = "手动选择",
                selected = manualMode,
                onClick = { manualMode = true },
            )
            RadioChip(
                label = "预制",
                selected = !manualMode,
                onClick = { manualMode = false },
            )
        }

        if (manualMode) {
            OutlinedTextField(
                value = manualSeconds,
                onValueChange = { manualSeconds = it.filter { c -> c.isDigit() } },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("手动倒计时时长（秒）") },
                placeholder = { PlaceholderInline("例如 120") },
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                presets.forEachIndexed { i, label ->
                    RadioChip(
                        label = label,
                        selected = presetIndex == i,
                        onClick = { presetIndex = i },
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if (manualMode && manualSeconds.isBlank()) {
                    "--:--"
                } else {
                    "%02d:%02d".format(totalSeconds / 60, totalSeconds % 60)
                },
                style = MaterialTheme.typography.displaySmall,
                fontFamily = FontFamily.Monospace,
            )
            Text(
                text = "注射：${if (injection) "已开启" else "关闭"} · 镜头：${if (lensConnected) "已连接" else "未连接"} · 磁场：${if (magneticField) "开" else "关"} · 蓝光：${if (blueLight) "开" else "关"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Button(
            onClick = { /* TODO: 启动倒计时并联动硬件 */ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("开始倒计时")
        }

        PlaceholderInline("硬件 SDK 接入占位：注射泵 / 镜头 / 磁场 / 蓝光 驱动待连接")
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}