package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.JobTier
import com.example.ui.components.GlassMetricCard
import com.example.ui.theme.*
import com.example.viewmodel.NavigationTab
import com.example.viewmodel.UiState

@Composable
fun DashboardScreen(
    state: UiState,
    onTabSelect: (NavigationTab) -> Unit,
    onToggleHabit: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedHabits = state.habits.count { it.isDone }
    val appliedCount = state.jobStatuses.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        // Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GoldDark.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(ObsidianCard, ObsidianBlack)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(GoldMuted.copy(alpha = 0.4f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "EXECUTIVE LAUNCHPAD • BENGALURU & GLOBAL",
                                color = GoldChampagne,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = "Get Hired into an MNC or Global Role in 14 Days.",
                            color = TextPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            lineHeight = 28.sp
                        )

                        Text(
                            text = "Verified Job IDs, real in-hand salary calculations, ATS keyword injections, and word-for-word interview answers pre-configured for BBA freshers.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { onTabSelect(NavigationTab.JOBS) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldMetallic,
                                    contentColor = ObsidianBlack
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("dashboard_explore_jobs_button")
                            ) {
                                Text(
                                    text = "16 Job IDs →",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { onTabSelect(NavigationTab.LINKEDIN) },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = CyanAccent
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = Brush.linearGradient(listOf(CyanAccent, ObsidianBorder))
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("dashboard_score_jd_button")
                            ) {
                                Text(
                                    text = "Score JD",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4 KPI Metrics
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GlassMetricCard(
                        title = "Verified Job IDs",
                        value = "16 Active",
                        subtitle = "Bengaluru & USD Remote",
                        icon = Icons.Default.Work,
                        accentColor = GoldChampagne,
                        modifier = Modifier.weight(1f)
                    )
                    GlassMetricCard(
                        title = "Fresher In-Hand",
                        value = "₹28k - ₹50k",
                        subtitle = "Up to ₹3.4L Global WFH",
                        icon = Icons.Default.Payments,
                        accentColor = EmeraldFinTech,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GlassMetricCard(
                        title = "Applications",
                        value = "$appliedCount Tracked",
                        subtitle = "Auto-saved locally",
                        icon = Icons.Default.FactCheck,
                        accentColor = CyanAccent,
                        modifier = Modifier.weight(1f)
                    )
                    GlassMetricCard(
                        title = "Daily 20-10-5 Protocol",
                        value = "$completedHabits / 6 Done",
                        subtitle = if (completedHabits == 6) "Protocol Complete!" else "Keep executing",
                        icon = Icons.Default.Bolt,
                        accentColor = if (completedHabits >= 4) EmeraldFinTech else RoseAlert,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Daily Protocol Section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = GoldChampagne,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Daily Action Checklist",
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            text = "$completedHabits / 6 Completed",
                            color = GoldChampagne,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "Complete these 6 non-negotiable steps each morning to trigger interview calls within 7 days:",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    state.habits.forEach { habit ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (habit.isDone) EmeraldDark.copy(alpha = 0.3f) else ObsidianSurface)
                                .border(
                                    1.dp,
                                    if (habit.isDone) EmeraldFinTech.copy(alpha = 0.4f) else ObsidianBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onToggleHabit(habit.id) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Checkbox(
                                checked = habit.isDone,
                                onCheckedChange = { onToggleHabit(habit.id) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = EmeraldFinTech,
                                    checkmarkColor = ObsidianBlack,
                                    uncheckedColor = TextMuted
                                ),
                                modifier = Modifier.testTag("habit_checkbox_${habit.id}")
                            )
                            Column {
                                Text(
                                    text = habit.title,
                                    color = if (habit.isDone) EmeraldFinTech else TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = habit.description,
                                    color = TextSecondary,
                                    fontSize = 10.sp,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Speed vs Prestige Matrix
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "🏆 Speed vs. Prestige Tier Matrix",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    // Tier 1
                    TierCardRow(
                        tierTitle = "Tier 1: Fastest Offer (24-72 Hours)",
                        companies = "Infosys BPM, Genpact, DMart Walk-in",
                        pay = "In-Hand: ₹18.5k - ₹25.5k/mo + Cab",
                        color = EmeraldFinTech,
                        onExplore = { onTabSelect(NavigationTab.JOBS) }
                    )

                    // Tier 2
                    TierCardRow(
                        tierTitle = "Tier 2: Global GCCs (7-10 Days)",
                        companies = "Amazon, Siemens, Cigna, IBM, Northern Trust",
                        pay = "In-Hand: ₹24.5k - ₹39k/mo + Meals + Cab",
                        color = CyanAccent,
                        onExplore = { onTabSelect(NavigationTab.JOBS) }
                    )

                    // Tier 3
                    TierCardRow(
                        tierTitle = "Tier 3: Big 4 & Prestige Banks (2-3 Weeks)",
                        companies = "Target, Deloitte, Wells Fargo, JPM",
                        pay = "In-Hand: ₹34.5k - ₹50.5k/mo + Bonuses",
                        color = GoldChampagne,
                        onExplore = { onTabSelect(NavigationTab.JOBS) }
                    )

                    // Tier S
                    TierCardRow(
                        tierTitle = "Tier S: Global Remote USD Startups",
                        companies = "Clipboard Health ($30k-$50k USD), Revolut",
                        pay = "In-Hand: ₹80k - ₹3.4L/mo Remote",
                        color = Color(0xFFA855F7),
                        onExplore = { onTabSelect(NavigationTab.JOBS) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TierCardRow(
    tierTitle: String,
    companies: String,
    pay: String,
    color: Color,
    onExplore: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = tierTitle, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(text = companies, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(text = pay, color = TextSecondary, fontSize = 10.sp)
        }
        TextButton(
            onClick = onExplore,
            colors = ButtonDefaults.textButtonColors(contentColor = color)
        ) {
            Text(text = "View IDs →", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}
