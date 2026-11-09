package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.UiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SalaryCalculatorScreen(
    state: UiState,
    onCtcChange: (Float) -> Unit,
    onVarChange: (Float) -> Unit,
    onShiftChange: (Int) -> Unit,
    onPerksChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalCtc = state.calcAnnualCtc * 100_000f
    val variablePercent = state.calcVariablePercent
    val fixedBaseAnnual = totalCtc * (1f - (variablePercent / 100f))
    val grossMonthlyFixed = fixedBaseAnnual / 12f

    // Standard Indian deductions for freshers: 12% PF on Basic (Basic = ~50% of Fixed) + ₹200 Karnataka Professional Tax
    val basicMonthly = grossMonthlyFixed * 0.5f
    val pfMonthly = basicMonthly * 0.12f
    val ptMonthly = 200f
    val totalDeductionMonthly = pfMonthly + ptMonthly

    val guaranteedInHandMonthly = (grossMonthlyFixed - totalDeductionMonthly + state.calcShiftBonusPerMonth).toInt()
    val realLifestyleValueMonthly = guaranteedInHandMonthly + state.calcPerkSavingsPerMonth

    val isStartupTrap = variablePercent > 20f

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

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
                    text = "💰 In-Hand Salary & Startup Trap Calculator",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Calculate exact monthly bank credit after PF deductions, Karnataka Professional Tax, and shift allowances.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }

        // Live Calculated Outputs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, EmeraldFinTech.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = "Guaranteed In-Hand", color = EmeraldFinTech, fontSize = 11.sp)
                        Text(
                            text = "₹$guaranteedInHandMonthly / mo",
                            color = TextPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                        Text(text = "Hit bank account monthly", color = TextSecondary, fontSize = 9.sp)
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, GoldChampagne.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = "Real Lifestyle Value", color = GoldChampagne, fontSize = 11.sp)
                        Text(
                            text = "₹$realLifestyleValueMonthly / mo",
                            color = GoldChampagne,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                        Text(text = "Incl. cab & meal perks saved", color = TextSecondary, fontSize = 9.sp)
                    }
                }
            }
        }

        // Variable Pay Warning if > 20%
        item {
            AnimatedVisibility(visible = isStartupTrap) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, RoseAlert.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = RoseAlert.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = RoseAlert)
                        Column {
                            Text(
                                text = "⚠️ STARTUP VARIABLE TRAP WARNING",
                                color = RoseAlert,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Text(
                                text = "Over 20% of this CTC is tied to performance incentives. Demand the exact Fixed Base component in writing before signing!",
                                color = TextPrimary,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        // Sliders & Inputs
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // CTC Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Annual Offered CTC", color = TextSecondary, fontSize = 12.sp)
                            Text(
                                text = "₹${String.format("%.1f", state.calcAnnualCtc)} LPA",
                                color = GoldChampagne,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                        Slider(
                            value = state.calcAnnualCtc,
                            onValueChange = onCtcChange,
                            valueRange = 2.2f..15.0f,
                            steps = 128,
                            colors = SliderDefaults.colors(
                                thumbColor = GoldChampagne,
                                activeTrackColor = GoldChampagne,
                                inactiveTrackColor = ObsidianBorder
                            )
                        )
                    }

                    // Variable Percent Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Variable Pay / Incentive %", color = TextSecondary, fontSize = 12.sp)
                            Text(
                                text = "${state.calcVariablePercent.toInt()}% Variable",
                                color = if (isStartupTrap) RoseAlert else CyanAccent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                        Slider(
                            value = state.calcVariablePercent,
                            onValueChange = onVarChange,
                            valueRange = 0f..50f,
                            steps = 10,
                            colors = SliderDefaults.colors(
                                thumbColor = if (isStartupTrap) RoseAlert else CyanAccent,
                                activeTrackColor = if (isStartupTrap) RoseAlert else CyanAccent,
                                inactiveTrackColor = ObsidianBorder
                            )
                        )
                    }

                    // Shift selection
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Shift Allowance:", color = TextSecondary, fontSize = 11.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = state.calcShiftBonusPerMonth == 0,
                                onClick = { onShiftChange(0) },
                                label = { Text("Day (₹0)", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldMetallic)
                            )
                            FilterChip(
                                selected = state.calcShiftBonusPerMonth == 3500,
                                onClick = { onShiftChange(3500) },
                                label = { Text("UK (+₹3.5k)", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = CyanAccent)
                            )
                            FilterChip(
                                selected = state.calcShiftBonusPerMonth == 5000,
                                onClick = { onShiftChange(5000) },
                                label = { Text("US/Night (+₹5k)", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = EmeraldFinTech)
                            )
                        }
                    }

                    // Cab & Meals
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Free 2-Way AC Cab & Meal Card:", color = TextSecondary, fontSize = 11.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = state.calcPerkSavingsPerMonth == 5200,
                                onClick = { onPerksChange(5200) },
                                label = { Text("Yes (Saves ₹5,200/mo)", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = EmeraldFinTech)
                            )
                            FilterChip(
                                selected = state.calcPerkSavingsPerMonth == 0,
                                onClick = { onPerksChange(0) },
                                label = { Text("Self Commute (₹0)", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ObsidianSurface)
                            )
                        }
                    }
                }
            }
        }

        // Deduction Details & Negotiation Script
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Detailed Breakdown & Negotiation Script",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ObsidianSurface)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Annual Fixed Base:", color = TextSecondary, fontSize = 11.sp)
                            Text(text = "₹${fixedBaseAnnual.toInt()} / yr", color = TextPrimary, fontSize = 11.sp)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Estimated Monthly PF & PT Deduction:", color = RoseAlert, fontSize = 11.sp)
                            Text(text = "- ₹${totalDeductionMonthly.toInt()} / mo", color = RoseAlert, fontSize = 11.sp)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Monthly Shift Allowance Added:", color = EmeraldFinTech, fontSize = 11.sp)
                            Text(text = "+ ₹${state.calcShiftBonusPerMonth} / mo", color = EmeraldFinTech, fontSize = 11.sp)
                        }
                    }

                    Text(
                        text = "🗣️ How to Ask HR for ₹30,000 Extra (Word-for-Word):",
                        color = GoldChampagne,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "\"Thank you so much for the offer! I am thrilled to join the team in Bengaluru. Since I am available for immediate joining with zero notice period and bring strong Excel & operations readiness, is there room to nudge the fixed base up by ₹30,000–₹40,000, or include a one-time relocation/joining bonus?\"",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}
