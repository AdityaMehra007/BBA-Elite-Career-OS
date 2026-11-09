package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DataRepository
import com.example.model.UserCandidateProfile
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun LinkedInScorerScreen(
    state: UiState,
    onProfileUpdate: (UserCandidateProfile) -> Unit,
    onLoadJd: (String) -> Unit,
    onAutoInject: () -> Unit,
    modifier: Modifier = Modifier
) {
    var candidateName by remember(state.candidateProfile.name) { mutableStateOf(state.candidateProfile.name) }
    var candidateCollege by remember(state.candidateProfile.college) { mutableStateOf(state.candidateProfile.college) }
    var candidateContact by remember(state.candidateProfile.contact) { mutableStateOf(state.candidateProfile.contact) }
    var candidateUrl by remember(state.candidateProfile.linkedinUrl) { mutableStateOf(state.candidateProfile.linkedinUrl) }
    var candidateRawText by remember(state.candidateProfile.rawText) { mutableStateOf(state.candidateProfile.rawText) }

    var selectedJdKey by remember { mutableStateOf("siemens") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        // LinkedIn Importer Section
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
                        Text(
                            text = "in LinkedIn Data & Profile Importer",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(CyanAccent.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Auto-Sync", color = CyanAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Text(
                        text = "Edit or paste your LinkedIn credentials to automatically sync your resume and calculate ATS scores.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = candidateName,
                            onValueChange = {
                                candidateName = it
                                onProfileUpdate(state.candidateProfile.copy(name = it))
                            },
                            label = { Text("Candidate Full Name", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldChampagne,
                                unfocusedBorderColor = ObsidianBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        OutlinedTextField(
                            value = candidateCollege,
                            onValueChange = {
                                candidateCollege = it
                                onProfileUpdate(state.candidateProfile.copy(college = it))
                            },
                            label = { Text("College / University", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldChampagne,
                                unfocusedBorderColor = ObsidianBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = candidateContact,
                            onValueChange = {
                                candidateContact = it
                                onProfileUpdate(state.candidateProfile.copy(contact = it))
                            },
                            label = { Text("Email & Phone", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldChampagne,
                                unfocusedBorderColor = ObsidianBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        OutlinedTextField(
                            value = candidateUrl,
                            onValueChange = {
                                candidateUrl = it
                                onProfileUpdate(state.candidateProfile.copy(linkedinUrl = it))
                            },
                            label = { Text("LinkedIn Profile Link", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldChampagne,
                                unfocusedBorderColor = ObsidianBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )
                    }

                    Text(text = "Paste Raw LinkedIn About / Skills / Experience:", color = TextSecondary, fontSize = 11.sp)
                    OutlinedTextField(
                        value = candidateRawText,
                        onValueChange = {
                            candidateRawText = it
                            onProfileUpdate(state.candidateProfile.copy(rawText = it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .testTag("linkedin_raw_text_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldChampagne,
                            unfocusedBorderColor = ObsidianBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Button(
                        onClick = onAutoInject,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldFinTech,
                            contentColor = ObsidianBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("load_elite_preset_button")
                    ) {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Load 98% ATS Elite BBA Preset & Sync", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Live JD ATS Scorer Section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GoldChampagne.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "🎯 Live JD Analyzer & ATS Keyword Scorer",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Text(
                        text = "Select any official job description to benchmark your keyword match percentage:",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    // JD Selector Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = {
                                selectedJdKey = "siemens"
                                val job = DataRepository.VERIFIED_JOBS.first { it.company == "Siemens" }
                                onLoadJd(job.fullJd + "\n\nKeywords: " + job.keywords.joinToString())
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedJdKey == "siemens") GoldMetallic else ObsidianSurface,
                                contentColor = if (selectedJdKey == "siemens") ObsidianBlack else TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Siemens", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                selectedJdKey = "cigna"
                                val job = DataRepository.VERIFIED_JOBS.first { it.company == "Cigna Healthcare" }
                                onLoadJd(job.fullJd + "\n\nKeywords: " + job.keywords.joinToString())
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedJdKey == "cigna") GoldMetallic else ObsidianSurface,
                                contentColor = if (selectedJdKey == "cigna") ObsidianBlack else TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cigna", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                selectedJdKey = "amazon"
                                val job = DataRepository.VERIFIED_JOBS.first { it.role.contains("FBA") }
                                onLoadJd(job.fullJd + "\n\nKeywords: " + job.keywords.joinToString())
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedJdKey == "amazon") GoldMetallic else ObsidianSurface,
                                contentColor = if (selectedJdKey == "amazon") ObsidianBlack else TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Amazon", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                selectedJdKey = "deloitte"
                                val job = DataRepository.VERIFIED_JOBS.first { it.company == "Deloitte" }
                                onLoadJd(job.fullJd + "\n\nKeywords: " + job.keywords.joinToString())
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedJdKey == "deloitte") GoldMetallic else ObsidianSurface,
                                contentColor = if (selectedJdKey == "deloitte") ObsidianBlack else TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Deloitte", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Score Display Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(ObsidianSurface)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "ATS Keyword Match Score", color = TextSecondary, fontSize = 10.sp)
                            Text(
                                text = "${state.atsScore}% Match",
                                color = EmeraldFinTech,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp
                            )
                        }
                        Button(
                            onClick = onAutoInject,
                            colors = ButtonDefaults.buttonColors(containerColor = GoldMetallic, contentColor = ObsidianBlack),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Inject Missing", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Linear progress indicator
                    LinearProgressIndicator(
                        progress = { state.atsScore / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = EmeraldFinTech,
                        trackColor = ObsidianBorder
                    )

                    // Matched Keywords
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "✓ Matched Keywords:", color = EmeraldFinTech, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Text(
                            text = if (state.matchedKeywords.isNotEmpty()) state.matchedKeywords.joinToString(" • ") else "None",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }

                    // Missing Keywords
                    if (state.missingKeywords.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "✗ Missing Keywords to Add:", color = RoseAlert, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Text(
                                text = state.missingKeywords.joinToString(" • "),
                                color = RoseAlert.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
