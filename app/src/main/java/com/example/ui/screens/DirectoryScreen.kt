package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DataRepository
import com.example.model.CompanyDirectoryItem
import com.example.ui.theme.*

@Composable
fun DirectoryScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSector by remember { mutableStateOf<String?>(null) }

    val filteredList = remember(searchQuery, selectedSector) {
        DataRepository.DIRECTORY_SECTORS.filter { item ->
            val matchesSector = selectedSector == null || item.sector == selectedSector
            val matchesQuery = searchQuery.isBlank() ||
                    item.sector.contains(searchQuery, ignoreCase = true) ||
                    item.roleType.contains(searchQuery, ignoreCase = true) ||
                    item.firms.any { it.contains(searchQuery, ignoreCase = true) }
            matchesSector && matchesQuery
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "220+ Global & Bangalore Company Directory",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Categorized across all 9 major global hiring pathways for BBA freshers.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Filter 220+ companies (e.g., Deloitte, Shell, Target, Amazon)...", fontSize = 12.sp, color = TextMuted) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldChampagne) },
                    modifier = Modifier.fillMaxWidth(),
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
            }
        }

        items(filteredList) { item ->
            DirectorySectorCard(item)
        }
    }
}

@Composable
private fun DirectorySectorCard(item: CompanyDirectoryItem) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.sector,
                    color = GoldChampagne,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GoldMuted.copy(alpha = 0.3f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${item.firms.size} Companies",
                        color = GoldChampagne,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "Primary Roles: ${item.roleType}", color = TextSecondary, fontSize = 11.sp)
                Text(text = "Hub Location: ${item.hubLocation}", color = CyanAccent, fontSize = 10.sp)
            }

            // Firm Chips
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Active Recruiters:", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                FlowRowLayout(firms = item.firms)
            }
        }
    }
}

@Composable
private fun FlowRowLayout(firms: List<String>) {
    Text(
        text = firms.joinToString(" • "),
        color = TextPrimary,
        fontSize = 11.sp,
        lineHeight = 18.sp
    )
}
