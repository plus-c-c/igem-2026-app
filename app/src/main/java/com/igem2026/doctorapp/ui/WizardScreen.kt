package com.igem2026.doctorapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.steps.ReagentLoadingScreen
import com.igem2026.doctorapp.ui.steps.Step0ScanScreen
import com.igem2026.doctorapp.ui.steps.Step1OverviewScreen
import com.igem2026.doctorapp.ui.steps.Step3TimingScreen

enum class DoctorStep(
    val zhTitle: String,
    val zhSubtitle: String,
    val enTitle: String,
    val enSubtitle: String,
) {
    SCAN("1 · 扫描二维码", "读取试剂/配置信息，载入对应说明书", "1 · Scan QR Code", "Read reagent/configuration data and load instructions"),
    OVERVIEW("2 · 总体说明书", "使用流程与安全须知", "2 · General Instructions", "Workflow and safety information"),
    REAGENTS("3 · 放入试剂", "请按顺序放入试剂。", "3 · Add Reagents", "Please add reagents in order."),
    TIMING("4 · 预混", "等待预混器绿灯亮起，即可完成预混。", "4 · Premixing", "Wait for the premixer's green light to complete premixing."),
}

@Composable
fun WizardScreen(consumeStatusInsets: Boolean = true) {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    val steps = DoctorStep.entries
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    var scanPayload by rememberSaveable { mutableStateOf("") }
    var reagentCompletedCount by rememberSaveable { mutableIntStateOf(0) }
    var showReagentPrompt by rememberSaveable { mutableStateOf(false) }
    var reagentPromptShown by rememberSaveable { mutableStateOf(false) }
    val current = steps[currentIndex]
    val isLast = currentIndex == steps.lastIndex

    LaunchedEffect(currentIndex) {
        if (steps[currentIndex] == DoctorStep.REAGENTS && !reagentPromptShown) {
            showReagentPrompt = true
            reagentPromptShown = true
        }
    }

    if (showReagentPrompt && current == DoctorStep.REAGENTS) {
        AlertDialog(
            onDismissRequest = { showReagentPrompt = false },
            title = { Text(if (english) "Add reagents in order" else "请按顺序放药") },
            text = {
                Text(
                    if (english) {
                        "After placing each reagent, tap its module to continue. The next step unlocks after all modules are complete."
                    } else {
                        "请按顺序放药，每放好一个试剂，点击对应模块进行下一步。全部完成后才能进入下一步。"
                    },
                )
            },
            confirmButton = {
                TextButton(onClick = { showReagentPrompt = false }) {
                    Text(if (english) "Got it" else "知道了")
                }
            },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (consumeStatusInsets) Modifier.statusBarsPadding() else Modifier)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
            ) {
                Text(
                    text = if (english) {
                        "Step ${currentIndex + 1} / ${steps.size}"
                    } else {
                        "步骤 ${currentIndex + 1} / ${steps.size}"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = if (english) current.enTitle else current.zhTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = if (english) current.enSubtitle else current.zhSubtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { (currentIndex + 1) / steps.size.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                ) {
                    OutlinedButton(
                        onClick = { currentIndex = (currentIndex - 1).coerceAtLeast(0) },
                        enabled = currentIndex > 0,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(if (english) "Previous" else "上一页")
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (isLast) {
                                currentIndex = steps.lastIndex
                            } else {
                                currentIndex = (currentIndex + 1).coerceAtMost(steps.lastIndex)
                            }
                        },
                        enabled = current != DoctorStep.REAGENTS || reagentCompletedCount == 5,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(if (english) {
                            if (isLast) "Done" else "Next"
                        } else {
                            if (isLast) "完成" else "下一页"
                        })
                    }
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (steps[currentIndex]) {
                DoctorStep.SCAN -> Step0ScanScreen(
                    payload = scanPayload,
                    onPayloadChange = { scanPayload = it },
                )
                DoctorStep.REAGENTS -> ReagentLoadingScreen(
                    completedCount = reagentCompletedCount,
                    onReagentClick = { index ->
                        if (index == reagentCompletedCount) {
                            reagentCompletedCount = (reagentCompletedCount + 1).coerceAtMost(5)
                        }
                    },
                )
                DoctorStep.OVERVIEW -> Step1OverviewScreen(payload = scanPayload)
                DoctorStep.TIMING -> Step3TimingScreen()
            }
        }
    }
}
