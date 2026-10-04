package com.igem2026.doctorapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.igem2026.doctorapp.ui.components.LabelValueRow
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.RadioChip
import com.igem2026.doctorapp.ui.components.SectionTitle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PreOrderScreen(
    onBack: () -> Unit,
    onOrderPlaced: () -> Unit,
) {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    var peptideIndex by rememberSaveable { mutableIntStateOf(0) }
    var lipidPeptideIndex by rememberSaveable { mutableIntStateOf(0) }
    var orderQuantity by rememberSaveable { mutableStateOf("") }

    val peptides = if (english) {
        listOf("Adsorption Peptide A", "Adsorption Peptide B", "Adsorption Peptide C")
    } else {
        listOf("吸附短肽 A", "吸附短肽 B", "吸附短肽 C")
    }
    val lipidPeptides = if (english) {
        listOf("Liposome-linking Peptide X", "Liposome-linking Peptide Y", "Liposome-linking Peptide Z")
    } else {
        listOf("脂质体肽段 X", "脂质体肽段 Y", "脂质体肽段 Z")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onBack) { Text(if (english) "← Back to Home" else "← 返回首页") }
            Column {
                Text(
                    text = if (english) "Place an Order" else "预订下单",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = if (english) "Adsorption Peptide · Liposome-linking Peptide" else "吸附短肽 · 脂质体连接肽段",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        SectionTitle(if (english) "Select Adsorption Peptide" else "吸附短肽（Adsorption Peptide）选择")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            peptides.forEachIndexed { i, label ->
                RadioChip(label = label, selected = peptideIndex == i, onClick = { peptideIndex = i })
            }
        }
        PlaceholderInline(if (english) "Adsorption peptides differ in binding affinity and specificity. Select one based on the use case and target site." else "不同吸附短肽对目标位点的结合亲和力与特异性存在差异，选型时请结合应用场景与目标位点特性。")

        SectionTitle(if (english) "Select Liposome-linking Peptide" else "脂质体连接肽段选择")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            lipidPeptides.forEachIndexed { i, label ->
                RadioChip(label = label, selected = lipidPeptideIndex == i, onClick = { lipidPeptideIndex = i })
            }
        }
        PlaceholderInline(if (english) "Liposome-linking peptides differ in delivery function and payload capacity. Refer to the product specifications." else "脂质体连接肽段的递送功能与载荷能力各有侧重，选型时请对照产品参数说明。")

        SectionTitle(if (english) "Order Quantity" else "预订数量")
        OutlinedTextField(
            value = orderQuantity,
            onValueChange = { orderQuantity = it.filter { c -> c.isDigit() } },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(if (english) "Quantity (mg / vial)" else "数量（mg / 支）") },
            placeholder = { PlaceholderInline(if (english) "e.g. 10" else "例如 10") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        SectionTitle(if (english) "Order Summary" else "下单摘要")
        LabelValueRow(label = if (english) "Adsorption Peptide" else "吸附短肽", value = peptides[peptideIndex])
        LabelValueRow(label = if (english) "Liposome-linking Peptide" else "脂质体连接肽段", value = lipidPeptides[lipidPeptideIndex])
        LabelValueRow(
            label = if (english) "Quantity" else "数量",
            value = if (orderQuantity.isBlank()) {
                if (english) "Not entered" else "未填写"
            } else {
                if (english) "$orderQuantity mg/vial" else "$orderQuantity mg/支"
            },
        )

        Button(
            onClick = onOrderPlaced,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (english) "Submit Order" else "提交预订（下单）")
        }

        PlaceholderInline(if (english) "After submission, the operating instructions open automatically. The project team processes order details." else "提交后自动转入「操作说明」流程，订单信息由项目组统一处理。")
    }
}
