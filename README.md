# igem-2026-app

iGEM 2026 面向医生的 Android 应用（框架原型）。

## 步骤导航

应用为单 Activity + Jetpack Compose 向导式界面，底部 **上一页 / 下一页**（Next/Previous）按钮切换 4 个步骤：

1. **总体说明书** — 应用用途、操作流程、安全须知
2. **混合调参** — 相关力学参数（佳霖建模给出），可调 / 显示选项
3. **混合计时** — 计时与可视化模拟（参考硬件示意图）
4. **注射与硬件** — 注射、连接硬件镜头、磁场 / 蓝光开关、倒计时（手动选择 / 预制）

缺少的文案统一以 `<placeholder_text>`（或具名 `<xxx_placeholder>`）标注。

## 构建

```bash
./gradlew assembleDebug
```

- Gradle wrapper: 8.13（`gradle.properties` 中 `org.gradle.java.home` 指向 JDK 21）
- AGP 8.13.2 / Kotlin 2.2.21 / Compose BOM 2025.11.01

用 Android Studio 打开本目录即可同步运行。