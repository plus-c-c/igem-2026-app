package com.igem2026.doctorapp.ui.steps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.scale
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igem2026.doctorapp.ui.AppLanguage
import com.igem2026.doctorapp.ui.LocalAppLanguage

@Composable
fun ReagentLoadingScreen(
    completedCount: Int,
    onReagentClick: (Int) -> Unit,
) {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    val reagentSequence = listOf("PBS", "SELP", "Ru", "sPS", if (english) "Functional Module" else "功能模块")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = if (english) {
                "After adding each reagent, tap its module to continue."
            } else {
                "每放好一个试剂，请点击对应模块进行下一步。"
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )

        reagentSequence.forEachIndexed { index, reagent ->
            ReagentCard(
                number = index + 1,
                reagent = reagent,
                completed = index < completedCount,
                enabled = index == completedCount,
                onClick = { onReagentClick(index) },
            )
            if (index < reagentSequence.lastIndex) {
                Text(
                    text = "↓",
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

@Composable
private fun ReagentCard(
    number: Int,
    reagent: String,
    completed: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val cardColor by animateColorAsState(
        targetValue = if (completed) Color(0xFFE5F7EC) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        label = "reagent-card-color",
    )
    val borderColor by animateColorAsState(
        targetValue = if (completed) Color(0xFF62B77B) else MaterialTheme.colorScheme.outlineVariant,
        label = "reagent-card-border",
    )
    val scale by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.99f,
        label = "reagent-card-scale",
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = cardColor,
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Surface(
                shape = CircleShape,
                color = if (completed) Color(0xFF2E9B50) else MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    text = if (completed) "✓" else number.toString(),
                    modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = reagent,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
