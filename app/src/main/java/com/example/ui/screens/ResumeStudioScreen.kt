package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun ResumeStudioScreen(
    state: UiState,
    onPresetSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    var isCopied by remember { mutableStateOf(false) }

    val preset = state.resumePreset
    val candidate = state.candidateProfile

    val summaryText = when (preset) {
        "amazon" -> "Fast-paced and analytical BBA graduate specializing in E-Commerce Catalog Quality, Data Auditing, and Risk Investigation. Expert in Standard Operating Procedures (SOPs), ASIN error resolution, Advanced MS Excel (VLOOKUP, Pivot Tables), and cross-functional escalation handling within strict Turnaround Times (TAT)."
        "sales" -> "High-energy, target-driven BBA graduate with sharp consultative communication, inside sales prospecting, and customer relationship management skills. Proven track record in lead generation, outbound pitching, CRM tracking (Salesforce/LeadSquared), and closing B2B/B2C accounts."
        else -> "Analytical and detail-oriented Bachelor of Business Administration (BBA) graduate with strong proficiency in Advanced MS Excel (VLOOKUP, XLOOKUP, Pivot Tables), Financial & Business Operations (P2P, O2C, R2R), Data Reconciliation, and Stakeholder Management. Proven ability to execute Standard Operating Procedures (SOPs), maintain 99%+ data accuracy, and meet strict Service Level Agreements (SLAs)."
    }

    val expTitle = when (preset) {
        "amazon" -> "Catalog Quality & Risk Operations Intern (Academic Project)"
        "sales" -> "Business Development & Inside Sales Associate Trainee"
        else -> "Business Operations & Finance Trainee (Academic Exposure)"
    }

    val expBullets = when (preset) {
        "amazon" -> listOf(
            "Audited and resolved listing discrepancies for 1,200+ product catalog SKUs using Excel VLOOKUP and root-cause analysis.",
            "Enforced strict quality and brand compliance guidelines, maintaining a 99.2% accuracy rate across verification batches.",
            "Handled operational ticket escalations from cross-functional teams, reducing resolution turnaround time (TAT) by 25%."
        )
        "sales" -> listOf(
            "Prospected and qualified 250+ high-potential accounts via cold outreach and consultative discovery calls.",
            "Conducted product walkthroughs and managed pipeline progression in CRM, achieving a 22% conversion rate.",
            "Exceeded weekly outbound touchpoint quotas by 15% through structured pitch scripts and objection handling."
        )
        else -> listOf(
            "Analyzed, validated, and reconciled 500+ rows of financial, billing, and vendor transaction data using MS Excel (VLOOKUP & Pivot Tables), reducing reporting discrepancies by 18%.",
            "Executed end-to-end invoice verification, purchase order (PO) matching, and documentation workflows with 99.4% accuracy under strict SOPs.",
            "Collaborated with cross-functional stakeholders to resolve billing and operational exceptions within a 24-hour Turnaround Time (TAT)."
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "📄 Live ATS Resume Studio",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Select an industry preset to auto-tailor keywords and bullet points for instant export:",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                // Preset Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = preset == "ops",
                        onClick = { onPresetSelect("ops") },
                        label = { Text("Finance/Ops", fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldMetallic)
                    )
                    FilterChip(
                        selected = preset == "amazon",
                        onClick = { onPresetSelect("amazon") },
                        label = { Text("Amazon Catalog", fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = CyanAccent)
                    )
                    FilterChip(
                        selected = preset == "sales",
                        onClick = { onPresetSelect("sales") },
                        label = { Text("Startup Sales/BDA", fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = EmeraldFinTech)
                    )
                }
            }
        }

        // Clean White ATS Preview Sheet
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, ObsidianBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Header
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = candidate.name.uppercase(),
                            color = Color.Black,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Bengaluru, Karnataka | ${candidate.contact} | ${candidate.linkedinUrl}",
                            color = Color(0xFF334155),
                            fontSize = 9.sp
                        )
                    }

                    Divider(color = Color.Black, thickness = 1.dp)

                    // Summary Section
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "PROFESSIONAL SUMMARY",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = summaryText,
                            color = Color(0xFF1E293B),
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }

                    Divider(color = Color(0xFFCBD5E1), thickness = 0.5.dp)

                    // Skills Section
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "CORE SKILLS & ATS KEYWORDS",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "• Business Operations: P2P, O2C, R2R, Account Reconciliation, Invoicing, KYC/AML, SLA/TAT adherence, SOPs\n• Analytical & Tools: Advanced MS Excel (VLOOKUP, Pivot Tables), SQL Basics, Power BI, Workday basics, Google Sheets",
                            color = Color(0xFF1E293B),
                            fontSize = 9.sp,
                            lineHeight = 13.sp
                        )
                    }

                    Divider(color = Color(0xFFCBD5E1), thickness = 0.5.dp)

                    // Experience Section
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = expTitle, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                            Text(text = "Bengaluru, India", color = Color.Black, fontSize = 9.sp)
                        }
                        expBullets.forEach { bullet ->
                            Text(
                                text = "• $bullet",
                                color = Color(0xFF1E293B),
                                fontSize = 9.sp,
                                lineHeight = 13.sp
                            )
                        }
                    }

                    Divider(color = Color(0xFFCBD5E1), thickness = 0.5.dp)

                    // Education Section
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Bachelor of Business Administration (BBA) — ${candidate.college}",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                            Text(text = "Fresher", color = Color.Black, fontSize = 9.sp)
                        }
                        Text(
                            text = "Relevant Coursework: Financial Accounting, Operations, Marketing, Business Analytics.",
                            color = Color(0xFF475569),
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }

        // Action Button
        item {
            Button(
                onClick = {
                    val fullResumeText = """
                        ${candidate.name.uppercase()}
                        Bengaluru, Karnataka | ${candidate.contact} | ${candidate.linkedinUrl}
                        
                        PROFESSIONAL SUMMARY
                        $summaryText
                        
                        CORE SKILLS & ATS KEYWORDS
                        • Business Operations: P2P, O2C, R2R, Account Reconciliation, Invoicing, KYC/AML, SLA, TAT, SOPs
                        • Analytical & Tools: Advanced MS Excel (VLOOKUP, Pivot Tables), SQL Basics, Power BI, Workday
                        
                        EXPERIENCE
                        $expTitle — Bengaluru, India
                        ${expBullets.joinToString("\n") { "• $it" }}
                        
                        EDUCATION
                        Bachelor of Business Administration (BBA) — ${candidate.college}
                    """.trimIndent()

                    clipboardManager.setText(AnnotatedString(fullResumeText))
                    isCopied = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldMetallic, contentColor = ObsidianBlack),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isCopied) "✓ Resume Copied to Clipboard!" else "Copy Formatted ATS Text for Application",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
