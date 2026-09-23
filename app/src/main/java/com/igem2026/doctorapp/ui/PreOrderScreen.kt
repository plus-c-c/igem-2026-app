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
    var peptideIndex by rememberSaveable { mutableIntStateOf(0) }
    var lipidPeptideIndex by rememberSaveable { mutableIntStateOf(0) }
    var orderQuantity by rememberSaveable { mutableStateOf("") }

    val peptides = listOf("<吸附短肽A>", "<吸附短肽B>", "<吸附短肽C>")
    val lipidPeptides = listOf("<脂质体肽段X>", "<脂质体肽段Y>", "<脂质体肽段Z>")

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
            TextButton(onClick = onBack) { Text("← 返回") }
            Column {
                Text(
                    text = "预订下单",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "吸附短肽 · 脂质体连接肽段",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        SectionTitle("吸附短肽（Adsorption Peptide）选择")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            peptides.forEachIndexed { i, label ->
                RadioChip(label = label, selected = peptideIndex == i, onClick = { peptideIndex = i })
            }
        }
        PlaceholderInline("<不同吸附短肽对目标位点亲和力 / 特异性差异说明占位>")

        SectionTitle("脂质体连接肽段选择")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            lipidPeptides.forEachIndexed { i, label ->
                RadioChip(label = label, selected = lipidPeptideIndex == i, onClick = { lipidPeptideIndex = i })
            }
        }
        PlaceholderInline("<脂质体连接肽段功能与载荷差异说明占位>")

        SectionTitle("预订数量")
        OutlinedTextField(
            value = orderQuantity,
            onValueChange = { orderQuantity = it.filter { c -> c.isDigit() } },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("数量（mg / 支）") },
            placeholder = { PlaceholderInline("例如 10") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        SectionTitle("下单摘要")
        LabelValueRow(label = "吸附短肽", value = peptides[peptideIndex])
        LabelValueRow(label = "脂质体连接肽段", value = lipidPeptides[lipidPeptideIndex])
        LabelValueRow(
            label = "数量",
            value = if (orderQuantity.isBlank()) "<未填写>" else "$orderQuantity mg/支",
        )

        Button(
            onClick = onOrderPlaced,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("提交预订（下单）")
        }

        PlaceholderInline("<pre_order_framework_placeholder：下单接口待接入 · 提交后转入应用界面>")
    }
}