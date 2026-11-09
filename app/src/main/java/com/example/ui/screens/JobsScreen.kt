package com.example.ui.screens

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DataRepository
import com.example.model.ApplicationStatus
import com.example.model.JobTier
import com.example.model.VerifiedJob
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun JobsScreen(
    state: UiState,
    onTierSelect: (JobTier?) -> Unit,
    onSearchChange: (String) -> Unit,
    onOpenModal: (VerifiedJob) -> Unit,
    onStatusChange: (String, ApplicationStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    var copiedJobId by remember { mutableStateOf<String?>(null) }

    val filteredJobs = remember(state.selectedTierFilter, state.searchQuery) {
        DataRepository.VERIFIED_JOBS.filter { job ->
            val matchesTier = state.selectedTierFilter == null || job.tier == state.selectedTierFilter
            val matchesSearch = state.searchQuery.isBlank() ||
                    job.company.contains(state.searchQuery, ignoreCase = true) ||
                    job.role.contains(state.searchQuery, ignoreCase = true) ||
                    job.id.contains(state.searchQuery, ignoreCase = true)
            matchesTier && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        // Search & Filter Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Verified Job Postings & Official JDs",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Pre-loaded with verified 2026 Job IDs in Bengaluru & USD Global Remote.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search by company, role, or Job ID...", fontSize = 12.sp, color = TextMuted) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldChampagne) },
                    trailingIcon = {
                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = TextMuted)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("jobs_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldChampagne,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedContainerColor = ObsidianSurface,
                        unfocusedContainerColor = ObsidianSurface,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                // Tier Filter Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = state.selectedTierFilter == null,
                        onClick = { onTierSelect(null) },
                        label = { Text("All (${DataRepository.VERIFIED_JOBS.size})", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GoldMetallic,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = ObsidianCard,
                            labelColor = TextSecondary
                        )
                    )
                    FilterChip(
                        selected = state.selectedTierFilter == JobTier.EASY,
                        onClick = { onTierSelect(JobTier.EASY) },
                        label = { Text("⭐ Fast Offer", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldFinTech,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = ObsidianCard,
                            labelColor = TextSecondary
                        )
                    )
                    FilterChip(
                        selected = state.selectedTierFilter == JobTier.HIGH_PAY,
                        onClick = { onTierSelect(JobTier.HIGH_PAY) },
                        label = { Text("💰 High In-Hand", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GoldChampagne,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = ObsidianCard,
                            labelColor = TextSecondary
                        )
                    )
                    FilterChip(
                        selected = state.selectedTierFilter == JobTier.REMOTE,
                        onClick = { onTierSelect(JobTier.REMOTE) },
                        label = { Text("🌍 Global Remote", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = ObsidianCard,
                            labelColor = TextSecondary
                        )
                    )
                }
            }
        }

        // Job Cards List
        items(filteredJobs, key = { it.id }) { job ->
            val status = state.jobStatuses[job.id] ?: ApplicationStatus.TO_APPLY
            JobCardItem(
                job = job,
                status = status,
                isCopied = copiedJobId == job.id,
                onCopyId = {
                    clipboardManager.setText(AnnotatedString(job.id))
                    copiedJobId = job.id
                },
                onOpenDetails = { onOpenModal(job) },
                onStatusChange = { newStatus -> onStatusChange(job.id, newStatus) }
            )
        }
    }
}

@Composable
private fun JobCardItem(
    job: VerifiedJob,
    status: ApplicationStatus,
    isCopied: Boolean,
    onCopyId: () -> Unit,
    onOpenDetails: () -> Unit,
    onStatusChange: (ApplicationStatus) -> Unit
) {
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
            // Header Row: Job ID & Difficulty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GoldMuted.copy(alpha = 0.4f))
                        .border(1.dp, GoldChampagne.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "ID: ${job.id}",
                        color = GoldChampagne,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = job.difficulty,
                    color = when (job.difficultyScore) {
                        1 -> EmeraldFinTech
                        2 -> CyanAccent
                        else -> GoldChampagne
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Company & Role
            Column {
                Text(
                    text = job.company,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = job.role,
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "📍 ${job.location} • Portal: ${job.portalName}",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }

            // Compensation Summary Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(ObsidianSurface)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Annual CTC", color = TextSecondary, fontSize = 10.sp)
                    Text(text = job.ctc, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Monthly In-Hand", color = TextSecondary, fontSize = 10.sp)
                    Text(text = job.inHand, color = EmeraldFinTech, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            // Perks summary
            Text(
                text = "🎁 Perks: ${job.perks}",
                color = CyanAccent,
                fontSize = 10.sp
            )

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onCopyId,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isCopied) EmeraldFinTech else GoldChampagne
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.linearGradient(
                                listOf(if (isCopied) EmeraldFinTech else GoldChampagne, ObsidianBorder)
                            )
                        ),
                        modifier = Modifier.testTag("copy_job_id_${job.id}")
                    ) {
                        Icon(
                            imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isCopied) "Copied!" else "Copy ID", fontSize = 11.sp)
                    }

                    Button(
                        onClick = onOpenDetails,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldMetallic,
                            contentColor = ObsidianBlack
                        ),
                        modifier = Modifier.testTag("open_job_details_${job.id}")
                    ) {
                        Text(text = "Full JD & Kit →", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Status Dropdown
                var statusExpanded by remember { mutableStateOf(false) }
                Box {
                    AssistChip(
                        onClick = { statusExpanded = true },
                        label = { Text(status.label, fontSize = 10.sp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = when (status) {
                                ApplicationStatus.TO_APPLY -> ObsidianSurface
                                ApplicationStatus.APPLIED -> CyanAccent.copy(alpha = 0.2f)
                                ApplicationStatus.ONLINE_TEST -> GoldChampagne.copy(alpha = 0.2f)
                                ApplicationStatus.INTERVIEWING -> Color(0xFFA855F7).copy(alpha = 0.2f)
                                ApplicationStatus.OFFER -> EmeraldFinTech.copy(alpha = 0.3f)
                            },
                            labelColor = when (status) {
                                ApplicationStatus.TO_APPLY -> TextSecondary
                                ApplicationStatus.APPLIED -> CyanAccent
                                ApplicationStatus.ONLINE_TEST -> GoldChampagne
                                ApplicationStatus.INTERVIEWING -> Color(0xFFA855F7)
                                ApplicationStatus.OFFER -> EmeraldFinTech
                            }
                        )
                    )
                    DropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        ApplicationStatus.values().forEach { s ->
                            DropdownMenuItem(
                                text = { Text(s.label) },
                                onClick = {
                                    onStatusChange(s)
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
