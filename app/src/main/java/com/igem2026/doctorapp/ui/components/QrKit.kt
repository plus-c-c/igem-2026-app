package com.igem2026.doctorapp.ui.components

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Looper
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.EncodeHintType
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import java.util.concurrent.Executors
import kotlin.math.max

const val TEST_QR_PAYLOAD = "IGEM-TEST-001"

fun qrImageBitmap(content: String, sizePx: Int = 640): Bitmap? = runCatching {
    val matrix = QRCodeWriter().encode(
        content,
        BarcodeFormat.QR_CODE,
        sizePx,
        sizePx,
        emptyMap<EncodeHintType, Any>(),
    )
    val bmp = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    for (x in 0 until sizePx) {
        for (y in 0 until sizePx) {
            bmp.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }
    bmp
}.getOrNull()

fun decodeQrBitmap(bitmap: Bitmap): String? = runCatching {
    val w = bitmap.width
    val h = bitmap.height
    val pixels = IntArray(w * h)
    bitmap.getPixels(pixels, 0, w, 0, 0, w, h)
    val luminance = ByteArray(w * h) { i ->
        val c = pixels[i]
        (((Color.red(c) + Color.green(c) + Color.blue(c)) / 3) and 0xFF).toByte()
    }
    val source = PlanarYUVLuminanceSource(luminance, w, h, 0, 0, w, h, false)
    val reader = MultiFormatReader()
    reader.setHints(
        mapOf(
            DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE),
            DecodeHintType.TRY_HARDER to true,
        ),
    )
    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
    val result = reader.decodeWithState(binaryBitmap)
    reader.reset()
    result.text
}.getOrNull()

@Composable
fun CameraQrPreview(
    modifier: Modifier = Modifier,
    throttleMs: Long = 800,
    onResult: (String) -> Unit,
    onUnavailable: () -> Unit = {},
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply { implementationMode = PreviewView.ImplementationMode.COMPATIBLE }
    }
    val analysisExecutor = remember { Executors.newSingleThreadExecutor() }
    val mainHandler = remember { android.os.Handler(Looper.getMainLooper()) }

    DisposableEffect(lifecycleOwner) {
        val future = ProcessCameraProvider.getInstance(context)
        val listener = Runnable {
            try {
                val provider = future.get()
                val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                var lastFrameAt = 0L
                analysis.setAnalyzer(analysisExecutor) { image ->
                    val now = System.currentTimeMillis()
                    if (now - lastFrameAt < throttleMs) {
                        image.close()
                        return@setAnalyzer
                    }
                    lastFrameAt = now
                    try {
                        val text = decodeImage(image)
                        if (!text.isNullOrBlank()) {
                            mainHandler.post { onResult(text) }
                        }
                    } finally {
                        image.close()
                    }
                }
                provider.unbindAll()
                provider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    analysis,
                )
            } catch (e: Exception) {
                mainHandler.post { onUnavailable() }
            }
        }
        future.addListener(listener, ContextCompat.getMainExecutor(context))
        onDispose {
            analysisExecutor.shutdown()
            runCatching { future.get().unbindAll() }
        }
    }

    AndroidView(factory = { previewView }, modifier = modifier.clipToBounds())
}

private fun decodeImage(image: ImageProxy): String? {
    val bmp = image.toBitmap()
    val maxDim = 700
    val scale = if (max(bmp.width, bmp.height) > maxDim) maxDim / max(bmp.width, bmp.height).toFloat() else 1f
    val scaled = if (scale < 1f) {
        Bitmap.createScaledBitmap(
            bmp,
            (bmp.width * scale).toInt().coerceAtLeast(1),
            (bmp.height * scale).toInt().coerceAtLeast(1),
            true,
        )
    } else {
        bmp
    }
    val text = decodeQrBitmap(scaled)
    if (scaled !== bmp) scaled.recycle()
    bmp.recycle()
    return text
}