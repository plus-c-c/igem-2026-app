package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.PLACEHOLDER
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.SectionTitle
import kotlinx.coroutines.delay

@Composable
fun Step3TimingScreen() {
    var elapsedSeconds by remember { mutableIntStateOf(0) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        while (running) {
            delay(1000)
            elapsedSeconds++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("混合计时", style = MaterialTheme.typography.titleLarge)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "%02d:%02d".format(elapsedSeconds / 60, elapsedSeconds % 60),
                style = MaterialTheme.typography.displayMedium,
                fontFamily = FontFamily.Monospace,
            )
            PlaceholderInline("当前混合阶段 <阶段名占位>")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = { running = !running },
                modifier = Modifier.weight(1f),
            ) {
                Text(if (running) "暂停" else "开始")
            }
            OutlinedButton(
                onClick = { elapsedSeconds = 0; running = false },
                modifier = Modifier.weight(1f),
            ) {
                Text("重置")
            }
        }

        SectionTitle("可视化模拟（参考硬件示意图）")

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "硬件示意图 · 可视化模拟",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = PLACEHOLDER,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Text(
            text = "<可视化模拟说明占位：展示混合过程/磁珠运动/流场等>",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}