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
import com.igem2026.doctorapp.ui.AppLanguage
import com.igem2026.doctorapp.ui.LocalAppLanguage
import com.igem2026.doctorapp.ui.components.PlaceholderInline
import com.igem2026.doctorapp.ui.components.SectionTitle
import com.igem2026.doctorapp.ui.components.TEST_QR_PAYLOAD

private data class ModuleDesc(val name: String, val desc: String)

private data class ProductDoc(
    val productName: String,
    val modules: List<ModuleDesc>,
    val process: List<String>,
    val safety: List<String>,
    val storage: List<String>,
)

private fun productDoc(payload: String, english: Boolean): ProductDoc? = when (payload) {
    TEST_QR_PAYLOAD -> if (english) ProductDoc(
        productName = "PBS-SELP-Ru-sPS Functional Module Instructions",
        modules = listOf(
            ModuleDesc(
                "sPS (Spider Silk Protein Module)",
                "A high-strength structural unit based on spider silk protein. It provides good biocompatibility, controlled degradation, shape stability after injection, and long-term mechanical support.",
            ),
            ModuleDesc(
                "SELP (Silk-Elastin-Like Polypeptide Module)",
                "A hybrid polypeptide combining silk strength with elastin flexibility. It provides elastic recovery, stress buffering, and temperature-responsive gelation.",
            ),
            ModuleDesc(
                "Ru (Photo-initiated Crosslinking Unit)",
                "A ruthenium-based photo-crosslinking system used with blue light. It enables adjustable crosslink density and mechanical strength under mild processing conditions.",
            ),
            ModuleDesc(
                "PBS (Phosphate-Buffered Saline Module)",
                "A physiological buffer that stabilizes pH and osmotic pressure while maintaining module solubility and stability throughout the workflow.",
            ),
        ),
        process = listOf(
            "Add PBS first, followed by SELP, Ru, sPS, and the functional module.",
            "Mix thoroughly and remove bubbles after each addition to avoid uneven local concentrations.",
            "After injection or encapsulation, enable the magnetic field as needed to guide protein orientation and distribution.",
            "Enable blue light and expose according to the configured parameters to complete crosslinking.",
            "Disable the magnetic field and blue light, then proceed to final observation and follow-up operations.",
        ),
        safety = listOf(
            "Wear a lab coat and gloves throughout the procedure; wear blue-light protective glasses during exposure.",
            "Keep away from strong magnetic interference. Personnel carrying metal objects should avoid the work area while the magnetic field is active.",
            "Avoid direct contact with strong acids, strong bases, and organic solvents.",
            "Dispose of used materials according to the biological-material waste procedure.",
        ),
        storage = listOf(
            "Store refrigerated at 2–8 °C and protected from light. Avoid repeated freeze-thaw cycles.",
            "Each batch is checked for purity, endotoxins, and mechanical performance before delivery; reports are linked through the QR code.",
        ),
    ) else ProductDoc(
        productName = "PBS-SELP-RU-sPS 功能模块使用说明书",
        modules = listOf(
            ModuleDesc(
                "sPS（蜘蛛丝蛋白模块）",
                "以蜘蛛丝蛋白为核心的高强度结构单元，具备良好的生物相容性与可控降解性，构成体系力学骨架，支持注射后形态稳定与长期力学支撑。",
            ),
            ModuleDesc(
                "SELP（丝-弹性蛋白样多肽模块）",
                "兼具丝绸强度与弹性蛋白柔韧的杂化多肽，赋予体系弹性回复与应力缓冲能力，并协同温度相关的凝胶化行为。",
            ),
            ModuleDesc(
                "Ru（光引发交联单元）",
                "钌基光引发交联体系，配套蓝光照射工艺，可按需调节交联密度与力学强度，交联过程温和、工艺窗口宽。",
            ),
            ModuleDesc(
                "PBS（磷酸盐缓冲液模块）",
                "生理缓冲体系，维持混合液 pH 与渗透压稳定，保障各模块在不同工序条件下的溶解性与稳定性。",
            ),
        ),
        process = listOf(
            "先加入 PBS，再依次加入 SELP、Ru、sPS 与功能模块。",
            "每次加入后充分混匀并排除气泡，避免局部浓度不均。",
            "完成注射或封装后，按需开启磁场，引导蛋白取向与空间分布。",
            "开启蓝光，按设定参数照射完成交联固化。",
            "关闭磁场与蓝光，进入成品观察与后续操作。",
        ),
        safety = listOf(
            "操作全程佩戴实验服与手套；蓝光照射期间务必佩戴蓝光防护镜。",
            "远离强磁干扰源；磁场开启期间，携带金属物品的人员应避免进入工作区。",
            "避免与强酸、强碱及有机溶剂直接接触。",
            "使用后废弃物按生物材料废弃物流程处置。",
        ),
        storage = listOf(
            "2–8 ℃ 避光冷藏，避免反复冻融。",
            "每批交付前完成纯度、内毒素与力学性能抽检，报告随附二维码关联。",
        ),
    )
    else -> null
}

@Composable
private fun ProductManualScreen(doc: ProductDoc, english: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = if (english) "General Instructions" else "总体说明书",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = doc.productName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )

        SectionTitle(if (english) "Functional Module Components" else "功能模块组成")
        doc.modules.forEach { module ->
            ManualCard(title = module.name, body = module.desc)
        }

        SectionTitle(if (english) "Procedure" else "使用流程")
        doc.process.forEachIndexed { index, step ->
            Text(
                text = "${index + 1}. $step",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        SectionTitle(if (english) "Safety Information" else "安全须知")
        doc.safety.forEach { item ->
            Text(
                text = "· $item",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        SectionTitle(if (english) "Storage and Quality Control" else "存储与质控")
        doc.storage.forEach { item ->
            Text(
                text = "· $item",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ManualCard(title: String, body: String) {
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

@Composable
fun Step1OverviewScreen(payload: String = "") {
    val english = LocalAppLanguage.current == AppLanguage.ENGLISH
    val doc = productDoc(payload, english)
    if (doc != null) {
        ProductManualScreen(doc, english)
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = if (english) "General Instructions" else "总体说明书",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = if (english) {
                "This app supports clinicians with parameter setup, timing, and hardware controls for reagent mixing, imaging, and injection workflows."
            } else {
                "本应用面向医生，围绕样本试剂的混合、成像与注射流程提供参数设定、计时与硬件控制。"
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle(if (english) "0. Instructions Source (QR Code)" else "0. 说明书来源（随二维码）")
        Text(
            text = if (english) {
                if (payload.isBlank()) "No QR code recognized. General instructions are displayed." else "Source QR code: $payload"
            } else {
                if (payload.isBlank()) "未识别二维码，当前显示通用说明书。" else "来源二维码：$payload"
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle(if (english) "1. Intended Use" else "1. 用途")
        Text(
            text = if (english) {
                if (payload.isBlank()) {
                    "Integrated guidance for adsorption-peptide coating and liposome delivery in target-system preparation."
                } else {
                    "Intended-use information is loaded from the recognized QR code."
                }
            } else {
                if (payload.isBlank()) {
                    "面向目标体系构建，提供吸附短肽包被与脂质体递送的一体化操作指引。"
                } else {
                    "用途信息随已识别二维码载入的产品数据实时显示。"
                }
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle(if (english) "2. Workflow Overview" else "2. 操作流程概览")
        Text(
            text = if (english) {
                "Step 3 adds PBS, SELP, Ru, sPS, and the functional module in order. Step 4 times mixing. Step 5 connects the camera, completes injection, and starts the observation countdown. Step 6 enables the magnetic field, Step 7 enables blue light, and Step 8 disables both. Use Place an Order to select adsorption and liposome-linking peptides."
            } else {
                "步骤 3 按 PBS、SELP、Ru、sPS、功能模块的顺序放入试剂；步骤 4 进行混合计时；步骤 5 连接镜头并完成注射、启动观察倒计时；步骤 6 打开磁场；步骤 7 打开蓝光；步骤 8 关闭磁场与蓝光；另可通过「预订下单」选择吸附短肽与脂质体连接肽段。"
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle(if (english) "3. Safety Information" else "3. 安全须知")
        Text(
            text = if (english) {
                if (payload.isBlank()) {
                    "Check equipment status before use. Wear a lab coat and gloves, and follow magnetic-field and blue-light safety precautions."
                } else {
                    "Follow the supplied instructions. Stop the procedure and report immediately if an abnormal condition occurs."
                }
            } else {
                if (payload.isBlank()) {
                    "操作前确认设备状态；全程佩戴实验服与手套；磁场与蓝光步骤期间执行对应防护。"
                } else {
                    "安全要求以随附说明书为准，异常情况立即终止操作并上报。"
                }
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        SectionTitle(if (english) "4. Order Option Selection Guide" else "4. 预订下单选项差异化说明书")
        PlaceholderInline(if (english) "Selection guidance for adsorption and liposome-linking peptides based on the intended application." else "针对不同吸附短肽与脂质体连接肽段，结合实际应用场景给出差异化选型说明。")
        Text(
            text = if (english) "Adsorption Peptide" else "吸附短肽",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        DiffCard(
            title = if (english) "Affinity and Specificity" else "亲和力与特异性",
            body = if (english) "Adsorption peptides differ in binding affinity and specificity. Select one according to the application." else "吸附短肽对目标位点的结合亲和力与特异性具有差异，需结合应用场景选型。",
        )
        DiffCard(
            title = if (english) "Stability and Storage" else "稳定性与存储",
            body = if (english) "Peptide stability and storage requirements vary by batch. Check the batch information linked to the QR code." else "短肽结构稳定性与存储条件因批次而异，可随二维码关联资料核对批次。",
        )
        Text(
            text = if (english) "Liposome-linking Peptide" else "脂质体连接肽段",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        DiffCard(
            title = if (english) "Function and Payload" else "功能与载荷",
            body = if (english) "Liposome-linking peptides differ in delivery function and payload capacity. Select one using the product specifications." else "脂质体连接肽段的递送功能与载荷能力各有侧重，请对照产品参数选型。",
        )
        DiffCard(
            title = if (english) "Crosslinking Compatibility" else "交联掺杂适配",
            body = if (english) "Evaluate peptide compatibility with the equipment and process parameters when using post-crosslinking incorporation." else "交联后掺杂工艺下，各肽段适配性需结合设备与工艺参数评估。",
        )
        PlaceholderInline(if (english) "See the supplied product information for peptide-specific parameters." else "各肽段差异化参数详见随附产品资料。")

        SectionTitle(if (english) "5. Disclaimer and Contact" else "5. 免责声明与联系人")
        Text(
            text = if (english) "These instructions are for internal project reference. Follow the latest batch procedure; contact the project team for assistance." else "本说明书为项目内部参考文件，具体操作以最新批次规程为准；如需协助，请联系项目组。",
            style = MaterialTheme.typography.bodyMedium,
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
