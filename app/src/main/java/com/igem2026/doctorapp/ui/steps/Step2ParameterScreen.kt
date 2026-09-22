package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.PLACEHOLDER
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.SectionTitle
import com.igem2026.doctorapp.ui.components.LabelValueRow

@Composable
fun Step2ParameterScreen() {
    var mixRatio by remember { mutableFloatStateOf(50f) }
    var flowRate by remember { mutableFloatStateOf(10f) }
    var temperature by remember { mutableFloatStateOf(25f) }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("混合调参", style = MaterialTheme.typography.titleLarge)
        Text(
            "相关力学参数由佳霖建模给出，本页仅提供框架性的可调 / 显示选项。",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SectionTitle("可调参数（医生可修改）")

        ParameterSlider(
            label = "<可调参数名_1 混合比>",
            value = mixRatio,
            valueRange = 0f..100f,
            onValueChange = { mixRatio = it },
        )
        ParameterSlider(
            label = "<可调参数名_2 流速>",
            value = flowRate,
            valueRange = 0f..50f,
            onValueChange = { flowRate = it },
        )
        ParameterSlider(
            label = "<可调参数名_3 温度>",
            value = temperature,
            valueRange = 0f..60f,
            onValueChange = { temperature = it },
            valueSuffix = "°C",
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("补充说明") },
            placeholder = { PlaceholderInline("输入额外参数说明（可选）") },
        )

        SectionTitle("显示参数（由建模结果回填 · 只读）")
        LabelValueRow("<显示参数_1 剪切应力>", PLACEHOLDER)
        LabelValueRow("<显示参数_2 粘度>", PLACEHOLDER)
        LabelValueRow("<显示参数_3 混合均匀度>", PLACEHOLDER)

        Text(
            "以上可调/显示项均为占位，待佳霖建模给出真实力学参数后替换。",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun ParameterSlider(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    valueSuffix: String = "",
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$label: ${value.toInt()}$valueSuffix",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}