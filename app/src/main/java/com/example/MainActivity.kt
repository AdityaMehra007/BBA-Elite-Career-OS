package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.JobDetailModal
import com.example.ui.components.TopLuxuryHeader
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.NavigationTab

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AureliusTheme {
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = ObsidianBlack,
                    topBar = {
                        TopLuxuryHeader(
                            candidateName = state.candidateProfile.name,
                            atsScore = state.atsScore,
                            onImportClick = { viewModel.setTab(NavigationTab.LINKEDIN) },
                            onExportPdfClick = { viewModel.setTab(NavigationTab.RESUME) },
                            modifier = Modifier.statusBarsPadding()
                        )
                    },
                    bottomBar = {
                        AureliusBottomNavBar(
                            currentTab = state.currentTab,
                            onTabSelected = { viewModel.setTab(it) },
                            modifier = Modifier.navigationBarsPadding()
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (state.currentTab) {
                            NavigationTab.DASHBOARD -> DashboardScreen(
                                state = state,
                                onTabSelect = { viewModel.setTab(it) },
                                onToggleHabit = { viewModel.toggleHabit(it) }
                            )
                            NavigationTab.LINKEDIN -> LinkedInScorerScreen(
                                state = state,
                                onProfileUpdate = { viewModel.updateCandidateProfile(it) },
                                onLoadJd = { viewModel.loadJdForScorer(it) },
                                onAutoInject = { viewModel.autoInjectMissingKeywords() }
                            )
                            NavigationTab.JOBS -> JobsScreen(
                                state = state,
                                onTierSelect = { viewModel.setTierFilter(it) },
                                onSearchChange = { viewModel.setSearchQuery(it) },
                                onOpenModal = { viewModel.openJobModal(it) },
                                onStatusChange = { id, status -> viewModel.updateJobStatus(id, status) }
                            )
                            NavigationTab.DIRECTORY -> DirectoryScreen()
                            NavigationTab.RESUME -> ResumeStudioScreen(
                                state = state,
                                onPresetSelect = { viewModel.setResumePreset(it) }
                            )
                            NavigationTab.SALARY -> SalaryCalculatorScreen(
                                state = state,
                                onCtcChange = { viewModel.setAnnualCtc(it) },
                                onVarChange = { viewModel.setVariablePercent(it) },
                                onShiftChange = { viewModel.setShiftBonus(it) },
                                onPerksChange = { viewModel.setPerkSavings(it) }
                            )
                            NavigationTab.BLUEPRINT -> BlueprintScreen()
                            NavigationTab.INTERVIEW -> InterviewOutreachScreen(
                                state = state,
                                onSelectQuestion = { viewModel.selectMockQuestion(it) },
                                onAnswerChange = { viewModel.setUserMockAnswer(it) },
                                onGradeAnswer = { viewModel.gradeMockAnswer() },
                                onNextVersantSentence = { viewModel.nextVersantSentence() },
                                onAddInterviewLog = { viewModel.addInterviewLog(it) },
                                onDeleteInterviewLog = { viewModel.deleteInterviewLog(it) }
                            )
                        }

                        // Detailed Modal Dialog if active
                        state.selectedJobForModal?.let { job ->
                            JobDetailModal(
                                job = job,
                                onDismiss = { viewModel.openJobModal(null) },
                                onTestInScorer = { jdText ->
                                    viewModel.loadJdForScorer(jdText)
                                    viewModel.setTab(NavigationTab.LINKEDIN)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AureliusBottomNavBar(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = ObsidianDark,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = ObsidianBorder)
    ) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavTabPill(
                tab = NavigationTab.DASHBOARD,
                icon = Icons.Default.Dashboard,
                isSelected = currentTab == NavigationTab.DASHBOARD,
                onClick = { onTabSelected(NavigationTab.DASHBOARD) }
            )
            NavTabPill(
                tab = NavigationTab.JOBS,
                icon = Icons.Default.Work,
                isSelected = currentTab == NavigationTab.JOBS,
                onClick = { onTabSelected(NavigationTab.JOBS) }
            )
            NavTabPill(
                tab = NavigationTab.LINKEDIN,
                icon = Icons.Default.Assessment,
                isSelected = currentTab == NavigationTab.LINKEDIN,
                onClick = { onTabSelected(NavigationTab.LINKEDIN) }
            )
            NavTabPill(
                tab = NavigationTab.RESUME,
                icon = Icons.Default.Description,
                isSelected = currentTab == NavigationTab.RESUME,
                onClick = { onTabSelected(NavigationTab.RESUME) }
            )
            NavTabPill(
                tab = NavigationTab.SALARY,
                icon = Icons.Default.Calculate,
                isSelected = currentTab == NavigationTab.SALARY,
                onClick = { onTabSelected(NavigationTab.SALARY) }
            )
            NavTabPill(
                tab = NavigationTab.DIRECTORY,
                icon = Icons.Default.Business,
                isSelected = currentTab == NavigationTab.DIRECTORY,
                onClick = { onTabSelected(NavigationTab.DIRECTORY) }
            )
            NavTabPill(
                tab = NavigationTab.BLUEPRINT,
                icon = Icons.Default.Map,
                isSelected = currentTab == NavigationTab.BLUEPRINT,
                onClick = { onTabSelected(NavigationTab.BLUEPRINT) }
            )
            NavTabPill(
                tab = NavigationTab.INTERVIEW,
                icon = Icons.Default.RecordVoiceOver,
                isSelected = currentTab == NavigationTab.INTERVIEW,
                onClick = { onTabSelected(NavigationTab.INTERVIEW) }
            )
        }
    }
}

@Composable
private fun NavTabPill(
    tab: NavigationTab,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = if (isSelected) ObsidianBlack else GoldChampagne
            )
        },
        label = {
            Text(
                text = tab.label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = GoldMetallic,
            selectedLabelColor = ObsidianBlack,
            containerColor = ObsidianCard,
            labelColor = TextSecondary
        ),
        modifier = Modifier.testTag("nav_pill_${tab.name.lowercase()}")
    )
}
