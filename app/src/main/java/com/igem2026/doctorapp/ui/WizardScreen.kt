package com.igem2026.doctorapp.ui

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
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.steps.Step1OverviewScreen
import com.igem2026.doctorapp.ui.steps.Step2ParameterScreen
import com.igem2026.doctorapp.ui.steps.Step3TimingScreen
import com.igem2026.doctorapp.ui.steps.Step4HardwareScreen

enum class DoctorStep(val title: String, val subtitle: String) {
    OVERVIEW("1 · 总体说明书", "使用流程与安全须知"),
    PARAMETER("2 · 混合调参", "相关力学参数（佳霖建模给出）"),
    TIMING("3 · 混合计时", "可视化模拟（参考硬件示意图）"),
    HARDWARE("4 · 注射与硬件", "连接镜头 · 磁场 · 蓝光 · 倒计时"),
}

@Composable
fun WizardScreen() {
    val steps = DoctorStep.entries
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val current = steps[currentIndex]
    val isLast = currentIndex == steps.lastIndex

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
            ) {
                Text(
                    text = "步骤 ${currentIndex + 1} / ${steps.size}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = current.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = current.subtitle,
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
                        Text("上一页")
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
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(if (isLast) "完成" else "下一页")
                    }
                }
            }
        },
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                fadeIn(tween(220)) togetherWith fadeOut(tween(150))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            label = "step-transition",
        ) { index ->
            when (steps[index]) {
                DoctorStep.OVERVIEW -> Step1OverviewScreen()
                DoctorStep.PARAMETER -> Step2ParameterScreen()
                DoctorStep.TIMING -> Step3TimingScreen()
                DoctorStep.HARDWARE -> Step4HardwareScreen()
            }
        }
    }
}