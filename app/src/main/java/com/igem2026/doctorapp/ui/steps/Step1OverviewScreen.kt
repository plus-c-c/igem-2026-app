package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.SectionTitle

@Composable
fun Step1OverviewScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = "总体说明书",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "本应用面向医生，围绕 <样本/试剂> 的 <混合-成像-注射> 流程提供参数设定、计时与硬件控制。",
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle("1. 用途")
        Text(
            text = "<application_purpose_placeholder>",
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle("2. 操作流程概览")
        Text(
            text = "步骤 2 设定混合参数；步骤 3 进行混合计时与可视化模拟；步骤 4 完成注射、连接硬件镜头、开关磁场/蓝光并启动倒计时。",
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle("3. 安全须知")
        Text(
            text = "<safety_instructions_placeholder>",
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle("4. 免责声明与联系人")
        Text(
            text = "<disclaimer_and_contact_placeholder>",
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = "注：本版本为框架原型，实际参数与文案将在后续版本补齐。",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}