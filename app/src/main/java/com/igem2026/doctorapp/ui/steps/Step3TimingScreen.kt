package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import kotlinx.coroutines.delay

@Composable
fun Step3TimingScreen() {
    var totalSecondsInput by remember { mutableStateOf("120") }
    val totalSeconds = totalSecondsInput.toIntOrNull()?.coerceAtLeast(0) ?: 0
    var elapsed by remember { mutableIntStateOf(0) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        if (running) {
            while (running && elapsed < totalSeconds) {
                delay(1000)
                elapsed += 1
            }
            running = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(240.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                progress = {
                    if (totalSeconds > 0) elapsed.toFloat() / totalSeconds else 0f
                },
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 16.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "%02d:%02d".format(elapsed / 60, elapsed % 60),
                    style = MaterialTheme.typography.displayLarge,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "混合计时",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        PlaceholderInline("当前混合阶段：进行中")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Button(
                onClick = { running = !running },
                modifier = Modifier.weight(1f),
                enabled = running || totalSeconds > 0,
            ) {
                Text(if (running) "暂停" else "开始")
            }
            OutlinedButton(
                onClick = {
                    running = false
                    elapsed = 0
                },
                modifier = Modifier.weight(1f),
                enabled = elapsed > 0 || running,
            ) {
                Text("重置")
            }
        }

        OutlinedTextField(
            value = totalSecondsInput,
            onValueChange = { totalSecondsInput = it.filter { c -> c.isDigit() } },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("混合总时长（秒）") },
            placeholder = { PlaceholderInline("例如 120") },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
        )
    }
}