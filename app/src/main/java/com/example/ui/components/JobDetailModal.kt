package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.VerifiedJob
import com.example.ui.theme.*

@Composable
fun JobDetailModal(
    job: VerifiedJob,
    onDismiss: () -> Unit,
    onTestInScorer: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .border(1.dp, GoldChampagne.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = ObsidianBlack),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GoldMuted.copy(alpha = 0.4f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "JOB ID: ${job.id}",
                            color = GoldChampagne,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Column {
                    Text(text = job.company, color = GoldChampagne, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = job.role, color = TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Text(text = "📍 ${job.location} • Portal: ${job.portalName}", color = TextSecondary, fontSize = 11.sp)
                }

                // Compensation Box
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(ObsidianSurface)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Annual CTC", color = TextSecondary, fontSize = 10.sp)
                        Text(text = job.ctc, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Monthly In-Hand", color = TextSecondary, fontSize = 10.sp)
                        Text(text = job.inHand, color = EmeraldFinTech, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                // Official JD Text
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Official Job Description (JD):", color = GoldChampagne, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianCard)
                            .padding(12.dp)
                    ) {
                        Text(text = job.fullJd, color = TextPrimary, fontSize = 11.sp, lineHeight = 16.sp)
                    }
                }

                // Expected CTC Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(EmeraldDark.copy(alpha = 0.3f))
                        .border(1.dp, EmeraldFinTech.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Text(text = "Exact Portal Expected CTC to Enter:", color = EmeraldFinTech, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    Text(text = job.portalCtc, color = TextPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    Text(text = "Using this prevents automated ATS salary gate filters from rejecting you.", color = TextSecondary, fontSize = 10.sp)
                }

                // Selection Rounds
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(ObsidianSurface)
                        .padding(12.dp)
                ) {
                    Text(text = "Selection Rounds to Expect:", color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    Text(text = job.rounds, color = TextPrimary, fontSize = 11.sp, lineHeight = 16.sp)
                }

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(job.id))
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldChampagne)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Copy ID", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            onTestInScorer(job.fullJd + "\n\nKeywords: " + job.keywords.joinToString())
                            onDismiss()
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1.5f),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldMetallic, contentColor = ObsidianBlack)
                    ) {
                        Text(text = "Test in Scorer →", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
