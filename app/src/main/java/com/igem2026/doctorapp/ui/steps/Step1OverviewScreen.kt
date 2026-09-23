package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.PlaceholderInline
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
            text = "步骤 2 进行混合计时与可视化模拟；步骤 3 连接镜头并完成注射、启动观察倒计时；步骤 4 打开磁场；步骤 5 打开蓝光；步骤 6 关闭磁场与蓝光；另可通过「预订下单」选择吸附短肽与脂质体连接肽段。",
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle("3. 安全须知")
        Text(
            text = "<safety_instructions_placeholder>",
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle("4. 预订下单选项差异化说明书")
        PlaceholderInline("针对不同吸附短肽与脂质体连接肽段，结合实际应用场景给出差异化选型说明。")
        Text(
            text = "吸附短肽",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        DiffCard(
            title = "亲和力与特异性",
            body = "<吸附短肽A/B/C 对目标位点亲和力、结合特异性差异说明占位>",
        )
        DiffCard(
            title = "稳定性与存储",
            body = "<短肽结构稳定性、存储条件差异说明占位>",
        )
        Text(
            text = "脂质体连接肽段",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        DiffCard(
            title = "功能与载荷",
            body = "<脂质体连接肽段X/Y/Z 的递送功能与载荷能力差异说明占位>",
        )
        DiffCard(
            title = "交联掺杂适配",
            body = "<交联后掺杂工艺下各肽段的适配性差异说明占位>",
        )
        PlaceholderInline("<framework：预订下单选项的详细差异化参数与临床文案将在后续版本补齐>")

        SectionTitle("5. 免责声明与联系人")
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

@Composable
private fun DiffCard(title: String, body: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}