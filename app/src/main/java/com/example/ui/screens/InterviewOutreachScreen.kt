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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DataRepository
import com.example.model.InterviewLog
import com.example.model.VerifiedJob
import com.example.ui.theme.*
import com.example.util.CalendarExporter
import com.example.viewmodel.UiState
import kotlinx.coroutines.delay

@Composable
fun InterviewOutreachScreen(
    state: UiState,
    onSelectQuestion: (Int) -> Unit,
    onAnswerChange: (String) -> Unit,
    onGradeAnswer: () -> Unit,
    onNextVersantSentence: () -> Unit,
    onAddInterviewLog: (InterviewLog) -> Unit,
    onDeleteInterviewLog: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var copiedItem by remember { mutableStateOf<String?>(null) }

    var selectedJobForDm by remember { mutableStateOf(DataRepository.VERIFIED_JOBS.first()) }

    // Versant timer state
    var isTimerRunning by remember { mutableStateOf(false) }
    var secondsRemaining by remember { mutableStateOf(15) }

    LaunchedEffect(isTimerRunning, secondsRemaining) {
        if (isTimerRunning && secondsRemaining > 0) {
            delay(1000L)
            secondsRemaining -= 1
        } else if (secondsRemaining == 0) {
            isTimerRunning = false
        }
    }

    val currentMock = DataRepository.MOCK_QUESTIONS[state.selectedMockQuestionIndex]
    val currentSentence = DataRepository.VERSANT_SENTENCES[state.versantSentenceIndex]

    val linkedInNote = "Hi, hope you're having a great week! I'm a BBA fresher in Bengaluru with strong Excel, data reconciliation, and operations skills. I noticed an opening at ${selectedJobForDm.company} for ${selectedJobForDm.role} (Job ID: ${selectedJobForDm.id}). Would you be open to referring me if I share my resume? Thank you!"

    val coldEmail = """
        Subject: BBA Fresher Application: ${selectedJobForDm.role} (Job ID: ${selectedJobForDm.id})
        
        Dear Hiring Team at ${selectedJobForDm.company},
        
        I am writing to express my strong enthusiasm for the ${selectedJobForDm.role} position (Job ID: ${selectedJobForDm.id}) in Bengaluru.
        
        As a Bachelor of Business Administration (BBA) graduate with hands-on proficiency in Advanced MS Excel (VLOOKUP, Pivot Tables), Financial Reconciliation, and Standard Operating Procedures (SOPs), I am equipped to deliver immediate operational accuracy and support your daily SLAs.
        
        I can join immediately with zero notice period. Please find my resume attached.
        
        Warm regards,
        ${state.candidateProfile.name} | ${state.candidateProfile.contact}
    """.trimIndent()

    // Form state for logging a new upcoming interview
    var showAddForm by remember { mutableStateOf(false) }
    var newCompany by remember { mutableStateOf("Deloitte") }
    var newRole by remember { mutableStateOf("Associate Analyst") }
    var newRound by remember { mutableStateOf("Round 1: Technical & Case") }
    var newDate by remember { mutableStateOf("2026-11-20") }
    var newTime by remember { mutableStateOf("15:00") }
    var newDuration by remember { mutableStateOf("45") }
    var newLink by remember { mutableStateOf("Google Meet / Teams Link") }
    var newNotes by remember { mutableStateOf("Review Golden Rules, P2P/O2C, and STAR behavioral answers.") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp)
    ) {
        // Section Title: Multi-Offer Factory
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GoldMuted.copy(alpha = 0.4f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "OFFER ENGINE",
                            color = GoldChampagne,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Multi-Offer Factory",
                        color = TextPrimary,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = "Manage upcoming interview rounds, automatically generate .ICS personal calendar files, drill Versant fluency, and execute cold referrals.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }

        // ==================== NEW SECTION: UPCOMING INTERVIEWS & .ICS CALENDAR GENERATOR ====================
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, GoldMetallic.copy(alpha = 0.6f), RoundedCornerShape(18.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(GoldMetallic.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = GoldChampagne,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Interview Calendar & .ICS Sync",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "${state.interviewLogs.size} Upcoming Round(s) Logged",
                                    color = EmeraldFinTech,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Button(
                            onClick = { showAddForm = !showAddForm },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (showAddForm) ObsidianSurface else GoldMetallic,
                                contentColor = if (showAddForm) TextPrimary else ObsidianBlack
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("toggle_add_interview_form")
                        ) {
                            Icon(
                                imageVector = if (showAddForm) Icons.Default.Close else Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (showAddForm) "Cancel" else "Log Interview",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "Log upcoming round details to instantly generate and import a standard .ICS event file into Google Calendar, Outlook, or Apple Calendar with automated 15-minute preparation alarms.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    // Permanent Input Card for Logging Upcoming Interviews with 'Generate Interview .ICS' button
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, GoldChampagne.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Log Upcoming Interview & Generate .ICS",
                                    color = GoldChampagne,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(GoldMuted.copy(alpha = 0.4f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "RFC 5545",
                                        color = GoldChampagne,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Quick Presets
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf("Siemens", "Amazon", "Deloitte", "Wells Fargo").forEach { comp ->
                                    AssistChip(
                                        onClick = {
                                            newCompany = comp
                                            val match = DataRepository.VERIFIED_JOBS.firstOrNull { it.company.contains(comp) }
                                            if (match != null) {
                                                newRole = match.role
                                                newNotes = "Key Prep Focus: " + match.keywords.take(4).joinToString(", ")
                                            }
                                        },
                                        label = { Text(comp, fontSize = 9.sp) },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = ObsidianCard,
                                            labelColor = if (newCompany == comp) GoldChampagne else TextSecondary
                                        )
                                    )
                                }
                            }

                            // Field: Company
                            OutlinedTextField(
                                value = newCompany,
                                onValueChange = { newCompany = it },
                                label = { Text("Company Name", fontSize = 11.sp) },
                                placeholder = { Text("e.g. Siemens, Amazon, Deloitte", fontSize = 11.sp, color = TextMuted) },
                                leadingIcon = {
                                    Icon(Icons.Default.Business, contentDescription = null, tint = GoldChampagne, modifier = Modifier.size(16.dp))
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("interview_company_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldChampagne,
                                    unfocusedBorderColor = ObsidianBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                )
                            )

                            // Field: Role
                            OutlinedTextField(
                                value = newRole,
                                onValueChange = { newRole = it },
                                label = { Text("Job Role / Position", fontSize = 11.sp) },
                                placeholder = { Text("e.g. Process Associate, Business Analyst", fontSize = 11.sp, color = TextMuted) },
                                leadingIcon = {
                                    Icon(Icons.Default.WorkOutline, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("interview_role_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldChampagne,
                                    unfocusedBorderColor = ObsidianBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                )
                            )

                            // Fields: Date & Time
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = newDate,
                                    onValueChange = { newDate = it },
                                    label = { Text("Date (YYYY-MM-DD)", fontSize = 11.sp) },
                                    placeholder = { Text("2026-11-20", fontSize = 11.sp, color = TextMuted) },
                                    leadingIcon = {
                                        Icon(Icons.Default.CalendarToday, contentDescription = null, tint = GoldChampagne, modifier = Modifier.size(16.dp))
                                    },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("interview_date_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = GoldChampagne,
                                        unfocusedBorderColor = ObsidianBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )

                                OutlinedTextField(
                                    value = newTime,
                                    onValueChange = { newTime = it },
                                    label = { Text("Time (HH:MM 24h)", fontSize = 11.sp) },
                                    placeholder = { Text("14:30", fontSize = 11.sp, color = TextMuted) },
                                    leadingIcon = {
                                        Icon(Icons.Default.Schedule, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                                    },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("interview_time_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = GoldChampagne,
                                        unfocusedBorderColor = ObsidianBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )
                            }

                            // Secondary Fields: Round & Duration
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = newRound,
                                    onValueChange = { newRound = it },
                                    label = { Text("Round Name / Stage", fontSize = 11.sp) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1.3f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = GoldChampagne,
                                        unfocusedBorderColor = ObsidianBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )

                                OutlinedTextField(
                                    value = newDuration,
                                    onValueChange = { newDuration = it },
                                    label = { Text("Duration (Mins)", fontSize = 11.sp) },
                                    singleLine = true,
                                    modifier = Modifier.weight(0.7f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = GoldChampagne,
                                        unfocusedBorderColor = ObsidianBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )
                            }

                            OutlinedTextField(
                                value = newLink,
                                onValueChange = { newLink = it },
                                label = { Text("Meeting Link / Location", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldChampagne,
                                    unfocusedBorderColor = ObsidianBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                )
                            )

                            // Explicit 'Generate Interview .ICS' Button
                            Button(
                                onClick = {
                                    if (newCompany.isNotBlank() && newDate.isNotBlank()) {
                                        val log = InterviewLog(
                                            company = newCompany.trim(),
                                            role = newRole.trim().ifEmpty { "Associate Analyst" },
                                            roundName = newRound.trim().ifEmpty { "Interview Round" },
                                            dateString = newDate.trim(),
                                            timeString = newTime.trim().ifEmpty { "11:00" },
                                            durationMinutes = newDuration.toIntOrNull() ?: 45,
                                            interviewerOrMeetingLink = newLink.trim().ifEmpty { "Online Interview" },
                                            notes = newNotes.trim()
                                        )
                                        onAddInterviewLog(log)
                                        // Generates .ICS via downloadBlob utility and triggers calendar prompt
                                        CalendarExporter.exportAndShareIcs(context, log)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldMetallic,
                                    contentColor = ObsidianBlack
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("generate_interview_ics_button")
                            ) {
                                Icon(Icons.Default.EventAvailable, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Generate Interview .ICS",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    // List of logged interviews
                    if (state.interviewLogs.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(ObsidianSurface)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No interviews logged yet. Click 'Log Interview' to add one and generate a .ICS file!",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            state.interviewLogs.forEach { log ->
                                InterviewLogCard(
                                    log = log,
                                    onExportIcs = {
                                        CalendarExporter.exportAndShareIcs(context, log)
                                    },
                                    onCopyIcs = {
                                        clipboardManager.setText(AnnotatedString(log.toIcsContent()))
                                        copiedItem = "ics_${log.id}"
                                    },
                                    isCopied = copiedItem == "ics_${log.id}",
                                    onDelete = { onDeleteInterviewLog(log.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Versant Speaking Drill
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pearson Versant Fluency Timer",
                            color = CyanAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(CyanAccent.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${secondsRemaining}s",
                                color = CyanAccent,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Text(
                        text = "Read aloud without pausing or repeating. The AI grader tests speaking pace and fluency:",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurface)
                            .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "\"$currentSentence\"",
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                secondsRemaining = 15
                                isTimerRunning = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = ObsidianBlack),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Start 15s Drill", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                onNextVersantSentence()
                                secondsRemaining = 15
                                isTimerRunning = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(ObsidianBorder)
                            )
                        ) {
                            Text(text = "Next Sentence →", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Mock Interview AI Grader
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GoldChampagne.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Live Interview Question Simulator",
                        color = GoldChampagne,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    // Question tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        DataRepository.MOCK_QUESTIONS.indices.forEach { idx ->
                            FilterChip(
                                selected = state.selectedMockQuestionIndex == idx,
                                onClick = { onSelectQuestion(idx) },
                                label = { Text("Q${idx + 1}", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = GoldMetallic,
                                    selectedLabelColor = ObsidianBlack
                                )
                            )
                        }
                    }

                    Text(
                        text = currentMock.question,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    OutlinedTextField(
                        value = state.userMockAnswer,
                        onValueChange = onAnswerChange,
                        placeholder = { Text("Type your spoken answer here to test clarity and STAR metrics...", fontSize = 11.sp, color = TextMuted) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldChampagne,
                            unfocusedBorderColor = ObsidianBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Button(
                        onClick = onGradeAnswer,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldMetallic, contentColor = ObsidianBlack),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Grade My Answer & Reveal 10/10 Script", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    // Score & Feedback Box
                    AnimatedVisibility(visible = state.mockFeedbackScore != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(ObsidianSurface)
                                .border(1.dp, EmeraldFinTech.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Score: ${state.mockFeedbackScore}/100",
                                    color = EmeraldFinTech,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(text = "Feedback Analysis", color = TextSecondary, fontSize = 10.sp)
                            }

                            Text(
                                text = state.mockFeedbackText ?: "",
                                color = TextPrimary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )

                            Divider(color = ObsidianBorder, thickness = 0.5.dp)

                            Text(
                                text = "🏆 10/10 Gold Standard Answer Script:",
                                color = GoldChampagne,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Text(
                                text = currentMock.winningScript,
                                color = TextSecondary,
                                fontSize = 10.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Referral DMs Generator
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
                        text = "📨 1-Click Referral DM & Cold Email Generator",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Select an active opening to tailor your LinkedIn connection note and cold email:",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    // Company Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Siemens", "Amazon", "Cigna", "Deloitte").forEach { compName ->
                            val jobMatch = DataRepository.VERIFIED_JOBS.firstOrNull { it.company.contains(compName) }
                                ?: DataRepository.VERIFIED_JOBS.first()
                            FilterChip(
                                selected = selectedJobForDm.company.contains(compName),
                                onClick = { selectedJobForDm = jobMatch },
                                label = { Text(compName, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldFinTech,
                                    selectedLabelColor = ObsidianBlack
                                )
                            )
                        }
                    }

                    // LinkedIn Note
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurface)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "LinkedIn 300-Char Connection Note:", color = GoldChampagne, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            TextButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(linkedInNote))
                                    copiedItem = "li"
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(text = if (copiedItem == "li") "✓ Copied" else "Copy Note", fontSize = 10.sp, color = CyanAccent)
                            }
                        }
                        Text(text = linkedInNote, color = TextPrimary, fontSize = 10.sp, lineHeight = 15.sp)
                    }

                    // Cold Email
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurface)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Cold InMail / Email to Hiring Manager:", color = EmeraldFinTech, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            TextButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(coldEmail))
                                    copiedItem = "email"
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(text = if (copiedItem == "email") "✓ Copied" else "Copy Email", fontSize = 10.sp, color = CyanAccent)
                            }
                        }
                        Text(text = coldEmail, color = TextPrimary, fontSize = 9.sp, lineHeight = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun InterviewLogCard(
    log: InterviewLog,
    onExportIcs: () -> Unit,
    onCopyIcs: () -> Unit,
    isCopied: Boolean,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(EmeraldDark)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = log.company,
                            color = EmeraldFinTech,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                    Text(
                        text = log.roundName,
                        color = GoldChampagne,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Remove interview",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = log.role,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "📅 ${log.dateString}",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "⏰ ${log.timeString} (${log.durationMinutes}m)",
                    color = CyanAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (log.notes.isNotBlank()) {
                Text(
                    text = "💡 ${log.notes}",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onExportIcs,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldMetallic,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier
                        .weight(1.3f)
                        .testTag("export_ics_button_${log.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Add to Calendar (.ICS)", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onCopyIcs,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isCopied) EmeraldFinTech else TextSecondary
                    ),
                    modifier = Modifier.weight(0.9f)
                ) {
                    Icon(
                        imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isCopied) "Copied" else "Copy ICS", fontSize = 10.sp)
                }
            }
        }
    }
}
