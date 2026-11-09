package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun BlueprintScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "🗺️ A-to-Z Execution Blueprints",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "The complete master blueprints from candidate profile optimization to Bangalore walk-in offers.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }

        // Blueprint 1
        item {
            BlueprintCard(
                tag = "BLUEPRINT 1 • DAYS 1 TO 3",
                title = "The Bangalore Recruiter Magnet Setup",
                points = listOf(
                    "Set LinkedIn Location strictly to 'Bengaluru, Karnataka, India' — 95% of corporate recruiters filter candidates strictly by city radius.",
                    "Headline Formula: 'BBA Graduate | Financial & Business Operations (P2P, O2C, R2R) | Advanced Excel (VLOOKUP, Pivot Tables) | Immediate Joiner'.",
                    "The 8:45 AM Naukri Algorithm Hack: Edit 1 period (.) in your profile summary every morning between 8:30 AM and 9:00 AM. Bangalore HRs log in at 9:30 AM and filter by 'Active Today' — you will rank #1 on their search screen.",
                    "Save two separate specialist PDFs: One for Finance/GCC Ops and one for Amazon/Catalog."
                ),
                color = GoldChampagne
            )
        }

        // Blueprint 2
        item {
            BlueprintCard(
                tag = "BLUEPRINT 2 • DAYS 4 TO 10",
                title = "Direct Portal & Referral Protocol",
                points = listOf(
                    "Never rely on 'Easy Apply' alone — it receives 2,000+ applicants per job. Always apply directly on official career portals using verified Job IDs.",
                    "The 5-Person Referral Rule: Search LinkedIn for '[Company Name] Bengaluru Analyst' and send the 300-char Referral DM from this app to 5 employees.",
                    "Expected CTC Portal Rule: When Workday asks for Expected CTC, enter 380000 for Tier 2 GCCs and 480000 for Tier 3 Banks so algorithms never auto-reject you."
                ),
                color = CyanAccent
            )
        }

        // Blueprint 3
        item {
            BlueprintCard(
                tag = "BLUEPRINT 3 • ONLINE ASSESSMENTS",
                title = "Cracking Pearson Versant & AMCAT",
                points = listOf(
                    "Amazon, Deloitte, IBM, and Accenture use the automated 20-minute Versant AI test for spoken English pace and fluency.",
                    "Zero Background Noise: Use wired earphones in a quiet room. Background noise triggers automated AI failure flags.",
                    "Sentence Repeat Section: Even if you forget 2 words in a long sentence, never pause or say 'umm' — speak smoothly with remaining words. Fluency carries 40% weight."
                ),
                color = EmeraldFinTech
            )
        }

        // Blueprint 4
        item {
            BlueprintCard(
                tag = "BLUEPRINT 4 • WALK-IN DRIVES",
                title = "Bangalore On-Site Walk-In Mastery",
                points = listOf(
                    "Best Walk-in Days: Tuesday, Wednesday, and Thursday mornings. Arrive by 9:15 AM at Infosys BPM Electronic City, Genpact Bellandur, or DMart Bommasandra.",
                    "Mandatory Folder: 5 printed copies of your tailored resume, Aadhaar & PAN originals + copies, and 3 passport photos.",
                    "Immediate Joiner Advantage: When HR asks availability, say: 'I am already in Bengaluru and can join this Monday morning.'"
                ),
                color = androidx.compose.ui.graphics.Color(0xFFA855F7)
            )
        }
    }
}

@Composable
private fun BlueprintCard(
    tag: String,
    title: String,
    points: List<String>,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = tag, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Text(text = title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            points.forEachIndexed { idx, pt ->
                Text(
                    text = "${idx + 1}. $pt",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
