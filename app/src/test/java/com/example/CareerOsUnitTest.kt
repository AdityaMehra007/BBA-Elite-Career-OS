package com.example

import com.example.data.DataRepository
import com.example.model.ApplicationStatus
import com.example.model.JobTier
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.NavigationTab
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CareerOsUnitTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun repository_hasVerifiedJobs() {
        assertTrue(DataRepository.VERIFIED_JOBS.isNotEmpty())
        assertEquals(16, DataRepository.VERIFIED_JOBS.size)
        val siemens = DataRepository.VERIFIED_JOBS.find { it.company == "Siemens" }
        assertNotNull(siemens)
        assertEquals("527834", siemens?.id)
    }

    @Test
    fun repository_hasDirectorySectors() {
        assertTrue(DataRepository.DIRECTORY_SECTORS.isNotEmpty())
        assertEquals(9, DataRepository.DIRECTORY_SECTORS.size)
    }

    @Test
    fun viewModel_toggleHabit_updatesState() {
        val initialDone = viewModel.uiState.value.habits.first().isDone
        viewModel.toggleHabit(1)
        val afterDone = viewModel.uiState.value.habits.first().isDone
        assertNotEquals(initialDone, afterDone)
    }

    @Test
    fun viewModel_updateJobStatus_tracksApplication() {
        viewModel.updateJobStatus("527834", ApplicationStatus.APPLIED)
        assertEquals(ApplicationStatus.APPLIED, viewModel.uiState.value.jobStatuses["527834"])
    }

    @Test
    fun viewModel_salaryCalculator_adjustsValues() {
        viewModel.setAnnualCtc(6.0f)
        assertEquals(6.0f, viewModel.uiState.value.calcAnnualCtc, 0.01f)
    }

    @Test
    fun viewModel_navigationTab_changes() {
        viewModel.setTab(NavigationTab.JOBS)
        assertEquals(NavigationTab.JOBS, viewModel.uiState.value.currentTab)
    }

    @Test
    fun interviewLog_generatesValidIcsCalendar() {
        val log = com.example.model.InterviewLog(
            id = "test-123",
            company = "Deloitte",
            role = "Associate Analyst",
            roundName = "Round 1: Technical",
            dateString = "2026-11-20",
            timeString = "15:00",
            durationMinutes = 45,
            interviewerOrMeetingLink = "https://teams.microsoft.com/test",
            notes = "Test notes"
        )
        val ics = log.toIcsContent()
        assertTrue(ics.contains("BEGIN:VCALENDAR"))
        assertTrue(ics.contains("SUMMARY:Interview: Deloitte - Associate Analyst (Round 1: Technical)"))
        assertTrue(ics.contains("DTSTART:20261120T150000"))
        assertTrue(ics.contains("DTEND:20261120T154500"))
        assertTrue(ics.contains("TRIGGER:-PT15M"))
        assertTrue(ics.contains("END:VCALENDAR"))
    }

    @Test
    fun viewModel_addAndDeleteInterviewLog() {
        val initialCount = viewModel.uiState.value.interviewLogs.size
        val log = com.example.model.InterviewLog(
            id = "new-cigna",
            company = "Cigna Healthcare",
            role = "Billing Associate",
            roundName = "Round 1",
            dateString = "2026-11-22",
            timeString = "10:00"
        )
        viewModel.addInterviewLog(log)
        assertEquals(initialCount + 1, viewModel.uiState.value.interviewLogs.size)
        assertTrue(viewModel.uiState.value.interviewLogs.any { it.id == "new-cigna" })

        viewModel.deleteInterviewLog("new-cigna")
        assertEquals(initialCount, viewModel.uiState.value.interviewLogs.size)
    }
}
