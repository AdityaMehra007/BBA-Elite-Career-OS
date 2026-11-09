package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.DataRepository
import com.example.model.ApplicationStatus
import com.example.model.JobTier
import com.example.model.UserCandidateProfile
import com.example.model.VerifiedJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NavigationTab(val label: String) {
    DASHBOARD("Dashboard"),
    LINKEDIN("LinkedIn & JD Scorer"),
    JOBS("Verified Job IDs"),
    DIRECTORY("220+ Directory"),
    RESUME("ATS Resume"),
    SALARY("In-Hand Calculator"),
    BLUEPRINT("A-to-Z Roadmap"),
    INTERVIEW("Multi-Offer Factory")
}

data class DailyHabitItem(
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean = false
)

data class UiState(
    val currentTab: NavigationTab = NavigationTab.DASHBOARD,
    val candidateProfile: UserCandidateProfile = UserCandidateProfile(),
    val jobStatuses: Map<String, ApplicationStatus> = emptyMap(),
    val habits: List<DailyHabitItem> = listOf(
        DailyHabitItem(1, "8:45 AM Naukri Refresh", "Re-upload resume or edit 1 dot (.) on Naukri to rank #1 at 9:30 AM."),
        DailyHabitItem(2, "Apply to 3 Verified Job IDs", "Submit applications for Amazon, Siemens, Cigna, IBM, or Target."),
        DailyHabitItem(3, "10 Cold LinkedIn Referral DMs", "Send personalized 300-char notes with Job ID to current employees."),
        DailyHabitItem(4, "20 Fresh Daily Portal Apps", "Filter LinkedIn & Naukri by Bengaluru + Past 24 Hours for Associate roles."),
        DailyHabitItem(5, "15-Min Versant & Excel Drill", "Practice the 5 technical accounting rules & speaking fluency."),
        DailyHabitItem(6, "1 Global Remote Application", "Apply to Clipboard Health, Revolut, or Wellfound remote positions.")
    ),
    val selectedTierFilter: JobTier? = null,
    val searchQuery: String = "",
    val activeJdText: String = "",
    val atsScore: Int = 92,
    val matchedKeywords: List<String> = emptyList(),
    val missingKeywords: List<String> = emptyList(),
    val selectedJobForModal: VerifiedJob? = null,
    // Salary Calculator State
    val calcAnnualCtc: Float = 4.5f, // LPA
    val calcVariablePercent: Float = 10f,
    val calcShiftBonusPerMonth: Int = 3500, // UK / US shift
    val calcPerkSavingsPerMonth: Int = 5200, // Cab + Meal Card
    // Mock Interview State
    val selectedMockQuestionIndex: Int = 0,
    val userMockAnswer: String = "",
    val mockFeedbackScore: Int? = null,
    val mockFeedbackText: String? = null,
    // Versant State
    val versantSentenceIndex: Int = 0,
    val resumePreset: String = "ops", // "ops", "amazon", "sales"
    // Interview Calendar & Multi-Offer Factory State
    val interviewLogs: List<com.example.model.InterviewLog> = listOf(
        com.example.model.InterviewLog(
            id = "demo-siemens",
            company = "Siemens",
            role = "Process Associate – Finance & Procurement",
            roundName = "Round 2: Technical P2P & Accounting",
            dateString = "2026-11-14",
            timeString = "14:30",
            durationMinutes = 45,
            interviewerOrMeetingLink = "https://teams.microsoft.com/l/meetup-join/siemens-interview",
            notes = "Review 3-way matching (PO, Invoice, GRN), VLOOKUP, and cash allocation workflows."
        ),
        com.example.model.InterviewLog(
            id = "demo-amazon",
            company = "Amazon",
            role = "FBA Catalog Specialist 1",
            roundName = "Round 2: Leadership Principles & SOPs",
            dateString = "2026-11-18",
            timeString = "11:00",
            durationMinutes = 60,
            interviewerOrMeetingLink = "https://chime.aws/amazon-interview-room",
            notes = "Prepare Customer Obsession & Ownership STAR stories for catalog anomaly resolution."
        )
    )
)

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Load default JD and calculate initial ATS score
        val defaultJob = DataRepository.VERIFIED_JOBS.first()
        loadJdForScorer(defaultJob.fullJd + "\n\nRequired Keywords: " + defaultJob.keywords.joinToString())
    }

    fun setTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun toggleHabit(id: Int) {
        _uiState.update { state ->
            val updated = state.habits.map {
                if (it.id == id) it.copy(isDone = !it.isDone) else it
            }
            state.copy(habits = updated)
        }
    }

    fun updateJobStatus(jobId: String, status: ApplicationStatus) {
        _uiState.update { state ->
            val updated = state.jobStatuses.toMutableMap()
            updated[jobId] = status
            state.copy(jobStatuses = updated)
        }
    }

    fun setTierFilter(tier: JobTier?) {
        _uiState.update { it.copy(selectedTierFilter = tier) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun openJobModal(job: VerifiedJob?) {
        _uiState.update { it.copy(selectedJobForModal = job) }
    }

    fun updateCandidateProfile(profile: UserCandidateProfile) {
        _uiState.update { it.copy(candidateProfile = profile) }
        calculateAtsScore()
    }

    fun loadJdForScorer(jd: String) {
        _uiState.update { it.copy(activeJdText = jd) }
        calculateAtsScore()
    }

    fun autoInjectMissingKeywords() {
        val eliteSummary = """
            Bachelor of Business Administration (BBA) Fresher in Bengaluru. Skilled in Advanced MS Excel (VLOOKUP, XLOOKUP, Pivot Tables, Conditional Formatting), Financial Accounting, Procure-to-Pay (P2P), Order-to-Cash (O2C), Record-to-Report (R2R), Account Reconciliation, Invoice Processing, Purchase Order, Billing, Enrollment, KYC, AML Compliance, Vendor Management, Inventory Management, Catalog, SLA, TAT, Standard Operating Procedures (SOPs), Root-Cause Analysis, Quality Assurance, Escalation Resolution, SQL Basics, Power BI, Workday, and Stakeholder Communication.
        """.trimIndent()
        _uiState.update {
            it.copy(
                candidateProfile = it.candidateProfile.copy(rawText = eliteSummary)
            )
        }
        calculateAtsScore()
    }

    private fun calculateAtsScore() {
        val profileText = (_uiState.value.candidateProfile.rawText + " " + _uiState.value.candidateProfile.title).lowercase()
        val jdText = _uiState.value.activeJdText.lowercase()

        val activeList = DataRepository.MASTER_KEYWORDS.filter { kw ->
            val token = kw.lowercase().split(" ")[0].replace("(", "").replace(")", "")
            jdText.contains(token)
        }.ifEmpty { DataRepository.MASTER_KEYWORDS.take(12) }

        val matched = mutableListOf<String>()
        val missing = mutableListOf<String>()

        activeList.forEach { kw ->
            val token = kw.lowercase().split(" ")[0].replace("(", "").replace(")", "")
            if (profileText.contains(token)) matched.add(kw) else missing.add(kw)
        }

        val score = if (activeList.isNotEmpty()) {
            ((matched.size.toFloat() / activeList.size.toFloat()) * 100).toInt().coerceIn(65, 99)
        } else 90

        _uiState.update {
            it.copy(
                atsScore = score,
                matchedKeywords = matched,
                missingKeywords = missing
            )
        }
    }

    // Salary Calculator Functions
    fun setAnnualCtc(ctc: Float) {
        _uiState.update { it.copy(calcAnnualCtc = ctc) }
    }

    fun setVariablePercent(percent: Float) {
        _uiState.update { it.copy(calcVariablePercent = percent) }
    }

    fun setShiftBonus(amount: Int) {
        _uiState.update { it.copy(calcShiftBonusPerMonth = amount) }
    }

    fun setPerkSavings(amount: Int) {
        _uiState.update { it.copy(calcPerkSavingsPerMonth = amount) }
    }

    // Mock Interview Functions
    fun selectMockQuestion(index: Int) {
        _uiState.update {
            it.copy(
                selectedMockQuestionIndex = index,
                userMockAnswer = "",
                mockFeedbackScore = null,
                mockFeedbackText = null
            )
        }
    }

    fun setUserMockAnswer(ans: String) {
        _uiState.update { it.copy(userMockAnswer = ans) }
    }

    fun gradeMockAnswer() {
        val ans = _uiState.value.userMockAnswer.trim()
        val score = when {
            ans.length > 200 -> 94
            ans.length > 100 -> 82
            ans.length > 30 -> 70
            else -> 45
        }
        val feedback = when {
            score >= 90 -> "Outstanding structure! Excellent use of domain metrics, STAR method, and confident corporate vocabulary."
            score >= 80 -> "Good foundation! Add specific operational tools (VLOOKUP, Pivot, SOPs) and quantifiable results to push to 95%."
            else -> "A bit brief. Corporate interviewers in Bangalore look for specific acronyms (P2P/SLA/TAT) and STAR context. Study the winning script below!"
        }
        _uiState.update {
            it.copy(mockFeedbackScore = score, mockFeedbackText = feedback)
        }
    }

    fun nextVersantSentence() {
        _uiState.update {
            val next = (it.versantSentenceIndex + 1) % DataRepository.VERSANT_SENTENCES.size
            it.copy(versantSentenceIndex = next)
        }
    }

    fun setResumePreset(preset: String) {
        _uiState.update { it.copy(resumePreset = preset) }
    }

    // Interview Log Calendar Actions
    fun addInterviewLog(log: com.example.model.InterviewLog) {
        _uiState.update { state ->
            state.copy(interviewLogs = listOf(log) + state.interviewLogs)
        }
    }

    fun deleteInterviewLog(id: String) {
        _uiState.update { state ->
            state.copy(interviewLogs = state.interviewLogs.filterNot { it.id == id })
        }
    }
}
