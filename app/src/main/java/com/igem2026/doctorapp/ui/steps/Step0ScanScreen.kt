package com.igem2026.doctorapp.ui.steps

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.core.content.ContextCompat
import com.igem2026.doctorapp.ui.components.CameraQrPreview

@Composable
fun Step0ScanScreen(
    payload: String,
    onPayloadChange: (String) -> Unit,
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED,
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted -> hasCameraPermission = granted }
    var cameraUnavailable by remember { mutableStateOf(false) }
    var draft by remember(payload) { mutableStateOf(payload) }
    var frameTop by remember { mutableFloatStateOf(0f) }
    val frameSize = 220.dp
    val framePx = with(LocalDensity.current) { frameSize.toPx() }
    val surfaceColor = MaterialTheme.colorScheme.surface
    val cameraActive = hasCameraPermission && !cameraUnavailable

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraActive) {
            CameraQrPreview(
                modifier = Modifier.fillMaxSize(),
                onResult = onPayloadChange,
                onUnavailable = { cameraUnavailable = true },
            )
        } else {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ) {}
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val left = (size.width - framePx) / 2f
            val top = frameTop
            drawRect(surfaceColor, topLeft = Offset(0f, 0f), size = Size(size.width, top))
            drawRect(surfaceColor, topLeft = Offset(0f, top + framePx), size = Size(size.width, size.height - top - framePx))
            drawRect(surfaceColor, topLeft = Offset(0f, top), size = Size(left, framePx))
            drawRect(surfaceColor, topLeft = Offset(left + framePx, top), size = Size(size.width - left - framePx, framePx))
        }
        if (!cameraActive) {
            Text(
                text = "未检测到摄像头\n真机 / 映射 webcam 可正常取景",
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.Center),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(frameSize)
                    .onGloballyPositioned { frameTop = it.positionInParent().y },
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(frameSize)
                        .border(2.dp, MaterialTheme.colorScheme.primary, RectangleShape),
                ) {}
            }
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "当前识别：${payload.ifBlank { "未识别" }}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                OutlinedTextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("二维码内容") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onPayloadChange(draft.trim()) }),
                )
            }
        }
    }
}