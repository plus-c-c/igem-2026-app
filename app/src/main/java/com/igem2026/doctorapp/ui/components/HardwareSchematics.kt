package com.igem2026.doctorapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LensPreviewWindow(connected: Boolean, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            if (connected) Color(0xFF2E7D32) else Color(0xFF9E9E9E),
                            CircleShape,
                        ),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "镜头画面（预留窗口）",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = if (connected) "已连接" else "未连接",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (connected) Color(0xFF2E7D32) else MaterialTheme.colorScheme.outline,
                )
            }
            Spacer(Modifier.size(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color(0xFF0E1116), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (connected) "实时镜头画面" else "镜头未连接",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFFECEFF1),
                    )
                    Spacer(Modifier.size(6.dp))
                    Text(
                        text = if (connected) {
                            "<camera_preview_placeholder：等待镜头 SDK 接入>"
                        } else {
                            "<waiting_for_hardware_lens_connection>"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF90A4AE),
                    )
                }
            }
        }
    }
}

@Composable
fun HardwareSchematicCard(
    title: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    diagram: @Composable (Modifier) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            if (enabled) Color(0xFF2E7D32) else Color(0xFF9E9E9E),
                            CircleShape,
                        ),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = if (enabled) "已开启" else "已关闭",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (enabled) Color(0xFF2E7D32) else MaterialTheme.colorScheme.outline,
                )
            }
            Spacer(Modifier.size(10.dp))
            diagram(Modifier.fillMaxWidth().aspectRatio(16f / 9f))
        }
    }
}

@Composable
fun MagneticFieldSchematic(enabled: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val poleTop = h * 0.16f
        val poleBottom = h * 0.84f
        val poleW = w * 0.13f
        val nX = w * 0.30f
        val sX = w * 0.70f

        val northRed = Color(0xFFD32F2F)
        val southBlue = Color(0xFF1976D2)
        val lineBlue = Color(0xFF0288D1)
        val gray = Color(0xFF90A4AE)
        val fieldAlpha = if (enabled) 1f else 0f

        drawRoundRect(
            color = if (enabled) northRed else gray.copy(alpha = 0.55f),
            topLeft = Offset(nX - poleW / 2, poleTop),
            size = Size(poleW, poleBottom - poleTop),
            cornerRadius = CornerRadius(6.dp.toPx()),
        )
        drawRoundRect(
            color = if (enabled) southBlue else gray.copy(alpha = 0.55f),
            topLeft = Offset(sX - poleW / 2, poleTop),
            size = Size(poleW, poleBottom - poleTop),
            cornerRadius = CornerRadius(6.dp.toPx()),
        )

        val leftEdge = nX + poleW / 2
        val rightEdge = sX - poleW / 2

        if (enabled) {
            val curves = listOf(
                FieldCurve(leftEdge, h * 0.28f, rightEdge, h * 0.28f, 40f),
                FieldCurve(leftEdge, h * 0.50f, rightEdge, h * 0.50f, 0f),
                FieldCurve(leftEdge, h * 0.72f, rightEdge, h * 0.72f, -40f),
            )
            curves.forEach { (fx, fy, tx, ty, bulge) ->
                drawFieldLine(fx, fy, tx, ty, bulge, lineBlue)
                val tipY = (fy + ty) / 2 - 0.75f * bulge
                drawArrowTip(Offset((fx + tx) / 2, tipY), rightEdge > fx, lineBlue)
            }
        }
    }
}

private data class FieldCurve(
    val fromX: Float,
    val fromY: Float,
    val toX: Float,
    val toY: Float,
    val bulge: Float,
)

private fun DrawScope.drawFieldLine(
    fromX: Float,
    fromY: Float,
    toX: Float,
    toY: Float,
    bulge: Float,
    color: Color,
) {
    val path = Path().apply {
        moveTo(fromX, fromY)
        cubicTo(
            fromX + (toX - fromX) * 0.35f, fromY - bulge,
            toX - (toX - fromX) * 0.35f, toY - bulge,
            toX, toY,
        )
    }
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round),
    )
}

private fun DrawScope.drawArrowTip(point: Offset, pointingRight: Boolean, color: Color) {
    val dir = if (pointingRight) 1f else -1f
    val head = 9.dp.toPx()
    val wing = 5.dp.toPx()
    val base = Offset(point.x - dir * head, point.y)
    drawLine(color, point, Offset(base.x, base.y - wing), strokeWidth = 2.5.dp.toPx())
    drawLine(color, point, Offset(base.x, base.y + wing), strokeWidth = 2.5.dp.toPx())
}

@Composable
fun BlueLightSchematic(enabled: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val gray = Color(0xFF90A4AE)
        val blue = Color(0xFF03A9F4)
        val deepBlue = Color(0xFF1565C0)

        val lampTop = h * 0.06f
        val lampBottom = h * 0.18f
        val lampL = w * 0.40f
        val lampR = w * 0.60f

        drawRect(
            color = if (enabled) gray.copy(alpha = 0.9f) else gray.copy(alpha = 0.5f),
            topLeft = Offset(w * 0.48f, 0f),
            size = Size(w * 0.04f, lampTop),
        )
        drawRoundRect(
            color = if (enabled) Color(0xFF546E7A) else gray.copy(alpha = 0.5f),
            topLeft = Offset(lampL, lampTop),
            size = Size(lampR - lampL, lampBottom - lampTop),
            cornerRadius = CornerRadius(6.dp.toPx()),
        )

        if (enabled) {
            drawCircle(
                color = blue.copy(alpha = 0.28f),
                radius = w * 0.22f,
                center = Offset(w / 2, lampBottom),
            )
            val cone = Path().apply {
                moveTo(lampL + 2.dp.toPx(), lampBottom)
                lineTo(lampR - 2.dp.toPx(), lampBottom)
                lineTo(w * 0.88f, h * 0.90f)
                lineTo(w * 0.12f, h * 0.90f)
                close()
            }
            drawPath(
                path = cone,
                brush = Brush.verticalGradient(
                    colors = listOf(blue.copy(alpha = 0.55f), deepBlue.copy(alpha = 0.06f)),
                    startY = lampBottom,
                    endY = h * 0.90f,
                ),
            )
            for (i in 0..4) {
                val x = w * (0.28f + i * 0.11f)
                drawLine(
                    color = Color.White.copy(alpha = 0.55f),
                    start = Offset(x, lampBottom + 4.dp.toPx()),
                    end = Offset(x, h * (0.22f + i * 0.15f)),
                    strokeWidth = 2.dp.toPx(),
                )
            }
            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = Offset(w / 2, (lampTop + lampBottom) / 2),
            )
        } else {
            drawRoundRect(
                color = gray.copy(alpha = 0.35f),
                topLeft = Offset(lampL, lampTop),
                size = Size(lampR - lampL, lampBottom - lampTop),
                cornerRadius = CornerRadius(6.dp.toPx()),
            )
        }

        drawRoundRect(
            color = if (enabled) Color(0xFF607D8B) else gray.copy(alpha = 0.45f),
            topLeft = Offset(w * 0.06f, h * 0.88f),
            size = Size(w * 0.88f, h * 0.08f),
            cornerRadius = CornerRadius(8.dp.toPx()),
        )
    }
}