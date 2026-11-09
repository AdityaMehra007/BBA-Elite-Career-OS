package com.example.model

data class VerifiedJob(
    val id: String,
    val company: String,
    val role: String,
    val location: String,
    val ctc: String,
    val inHand: String,
    val perks: String,
    val portalCtc: String,
    val difficulty: String,
    val difficultyScore: Int, // 1 to 5
    val tier: JobTier,
    val portalName: String,
    val rounds: String,
    val keywords: List<String>,
    val fullJd: String
)

enum class JobTier(val title: String) {
    EASY("⭐ Fast Offer (24h - 7d)"),
    HIGH_PAY("💰 High In-Hand (₹35k+/mo)"),
    REMOTE("🌍 100% Global Remote / USD")
}

data class CompanyDirectoryItem(
    val sector: String,
    val roleType: String,
    val hubLocation: String,
    val firms: List<String>
)

enum class ApplicationStatus(val label: String) {
    TO_APPLY("To Apply"),
    APPLIED("Applied"),
    ONLINE_TEST("Online Test"),
    INTERVIEWING("Interviewing"),
    OFFER("Offer Received! 🎉")
}

data class UserCandidateProfile(
    val name: String = "Aarav Sharma",
    val title: String = "BBA Fresher | Operations & Finance Analyst",
    val college: String = "Bengaluru University (BBA)",
    val contact: String = "+91 98765 43210 | aarav.bba@email.com",
    val linkedinUrl: String = "linkedin.com/in/aarav-bba",
    val rawText: String = """
        Bachelor of Business Administration (BBA) Fresher in Bengaluru. Skilled in Advanced MS Excel (VLOOKUP, XLOOKUP, Pivot Tables, Conditional Formatting), Financial Accounting, Procure-to-Pay (P2P), Order-to-Cash (O2C), Record-to-Report (R2R), Account Reconciliation, Invoice Processing, KYC, AML Compliance, Vendor Management, Inventory Management, Catalog, SLA, TAT, Standard Operating Procedures (SOPs), Root-Cause Analysis, Quality Assurance, Escalation Resolution, SQL Basics, Power BI, Workday, and Stakeholder Communication.
    """.trimIndent()
)

data class MockQuestionItem(
    val question: String,
    val category: String,
    val winningScript: String
)

data class InterviewLog(
    val id: String = java.util.UUID.randomUUID().toString(),
    val company: String,
    val role: String,
    val roundName: String, // e.g. "Round 1: HR & Aptitude", "Round 2: Technical/Operations", "Final Leadership"
    val dateString: String, // e.g. "2026-11-15"
    val timeString: String, // e.g. "14:30" (24h)
    val durationMinutes: Int = 45,
    val interviewerOrMeetingLink: String = "Google Meet / Microsoft Teams",
    val notes: String = "Prepare 3 Golden Rules, P2P/O2C, and Advanced Excel VLOOKUP/Pivot points."
) {
    fun toIcsContent(): String {
        // Formats YYYYMMDDTHHMMSS
        val cleanDate = dateString.replace("-", "")
        val cleanTime = timeString.replace(":", "") + "00"
        val startDateTime = "${cleanDate}T${cleanTime}"
        
        // Calculate end time
        val hour = timeString.substringBefore(":").toIntOrNull() ?: 10
        val minute = timeString.substringAfter(":").toIntOrNull() ?: 0
        val totalMinutes = hour * 60 + minute + durationMinutes
        val endHour = (totalMinutes / 60) % 24
        val endMinute = totalMinutes % 60
        val endDateTime = "${cleanDate}T${String.format("%02d%02d00", endHour, endMinute)}"
        
        return """
            BEGIN:VCALENDAR
            VERSION:2.0
            PRODID:-//Aurelius//Multi-Offer Factory Interview Scheduler//EN
            CALSCALE:GREGORIAN
            METHOD:PUBLISH
            BEGIN:VEVENT
            UID:${id}@aurelius.career.os
            DTSTAMP:${cleanDate}T000000Z
            DTSTART:${startDateTime}
            DTEND:${endDateTime}
            SUMMARY:Interview: $company - $role ($roundName)
            DESCRIPTION:${notes.replace("\n", " ")}\nLink: $interviewerOrMeetingLink
            LOCATION:$interviewerOrMeetingLink
            STATUS:CONFIRMED
            BEGIN:VALARM
            TRIGGER:-PT15M
            ACTION:DISPLAY
            DESCRIPTION:Reminder: Upcoming interview with $company in 15 minutes!
            END:VALARM
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()
    }
}

