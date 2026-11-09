package com.example.data

import com.example.model.CompanyDirectoryItem
import com.example.model.JobTier
import com.example.model.MockQuestionItem
import com.example.model.VerifiedJob

object DataRepository {

    val MASTER_KEYWORDS = listOf(
        "Procure-to-Pay (P2P)", "Order-to-Cash (O2C)", "Record-to-Report (R2R)",
        "Account Reconciliation", "Invoice Processing", "Purchase Order",
        "Billing", "Enrollment", "KYC", "AML Compliance", "Vendor Management",
        "Inventory Management", "Catalog", "MS Excel", "VLOOKUP", "Pivot Tables",
        "SLA", "TAT", "SOPs", "Root-Cause Analysis", "Quality Assurance",
        "Escalation Resolution", "Financial Accounting", "Workday", "Power BI",
        "SQL Basics", "Stakeholder Communication", "Data Validation"
    )

    val VERIFIED_JOBS = listOf(
        VerifiedJob(
            id = "527834",
            company = "Siemens",
            role = "Process Associate – Finance & Procurement Services",
            location = "Bengaluru (On-site)",
            ctc = "₹3.8 – ₹4.8 LPA",
            inHand = "₹28,500 – ₹35,500 / mo",
            perks = "Subsidized Meals + Shuttle Transport + ₹5L Insurance",
            portalCtc = "420000 (₹4.2 LPA)",
            difficulty = "⭐⭐ Medium",
            difficultyScore = 2,
            tier = JobTier.HIGH_PAY,
            portalName = "jobs.siemens.com",
            rounds = "Round 1: Aptitude/Excel Screen -> Round 2: P2P & Accounting Ops Interview -> Round 3: HR",
            keywords = listOf("Procure-to-Pay (P2P)", "Invoice Processing", "Account Reconciliation", "Purchase Order", "MS Excel", "VLOOKUP", "Vendor Management", "SLA"),
            fullJd = """
                Official Role Overview (Siemens Bengaluru - Job ID 527834):
                • Execute end-to-end Finance and Procurement Services (Procure-to-Pay / P2P and Record-to-Report / R2R) for global Siemens entities.
                • Verify vendor invoices against Purchase Orders (PO) and Goods Receipt Notes (3-way matching) with 99%+ accuracy.
                • Perform monthly account reconciliation, cash allocation, and vendor query resolution within agreed Service Level Agreements (SLAs) and Turnaround Times (TAT).
                • Utilize Advanced MS Excel (VLOOKUP, Pivot Tables) and ERP systems (SAP) to track open items and resolve billing discrepancies.
                • Eligibility: BBA / B.Com / BBM Freshers (0–1 year experience) with strong accounting fundamentals.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "26010627",
            company = "Cigna Healthcare",
            role = "Enrollment / Billing Associate Representative",
            location = "Bengaluru (On-site)",
            ctc = "₹3.6 – ₹4.5 LPA",
            inHand = "₹27,000 – ₹34,000 / mo",
            perks = "+₹4,000/mo US Shift Allowance + Free 2-Way Cab + 8% Bonus",
            portalCtc = "400000 (₹4.0 LPA)",
            difficulty = "⭐ Easy",
            difficultyScore = 1,
            tier = JobTier.EASY,
            portalName = "jobs.thecignagroup.com",
            rounds = "Round 1: English & Numerical Assessment -> Round 2: Operations Manager Interview",
            keywords = listOf("Billing", "Enrollment", "Account Reconciliation", "Invoice Processing", "MS Excel", "VLOOKUP", "SLA", "TAT", "Quality Assurance", "SOPs"),
            fullJd = """
                Official Role Overview (Cigna Healthcare Bengaluru - Job ID 26010627):
                • Manage client enrollment updates, policy billing, and premium reconciliation for global healthcare accounts.
                • Research and resolve billing discrepancies, unapplied cash, and client invoice adjustments using MS Excel and internal billing platforms.
                • Adhere strictly to Standard Operating Procedures (SOPs), HIPAA compliance, and daily Turnaround Time (TAT) targets.
                • Collaborate with cross-functional operations teams to ensure 99% quality accuracy.
                • Eligibility: Freshers (0–1 year) with a Bachelor's degree in Business (BBA/B.Com) and willingness to work rotational/US shifts.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "10587298",
            company = "Amazon",
            role = "FBA Catalog Specialist 1, IN FBA",
            location = "Bengaluru (ASSPL - B56)",
            ctc = "₹3.2 – ₹4.0 LPA",
            inHand = "₹24,500 – ₹30,000 / mo",
            perks = "+₹1,100 Zeta Meal Card + ₹1,250 Wi-Fi + Free 2-Way Cab",
            portalCtc = "360000 (₹3.6 LPA)",
            difficulty = "⭐ Easy",
            difficultyScore = 1,
            tier = JobTier.EASY,
            portalName = "amazon.jobs",
            rounds = "Round 1: Pearson Versant Test -> Round 2: Amazon Leadership Principles (STAR) Interview",
            keywords = listOf("Catalog", "MS Excel", "VLOOKUP", "Pivot Tables", "SOPs", "SLA", "TAT", "Data Validation", "Quality Assurance", "Root-Cause Analysis"),
            fullJd = """
                Official Role Overview (Amazon Bengaluru - Job ID 10587298):
                • Audit, enrich, and maintain product catalog data for Fulfillment by Amazon (FBA) sellers.
                • Troubleshoot catalog listing errors, ASIN merges, and inventory discrepancies using Standard Operating Procedures (SOPs) and MS Excel (VLOOKUP, Pivot Tables).
                • Meet daily productivity and quality Service Level Agreements (SLAs) while identifying root causes of recurring seller defects.
                • Communicate clearly with internal teams to resolve seller escalations within a 24-hour Turnaround Time (TAT).
            """.trimIndent()
        ),
        VerifiedJob(
            id = "10511626",
            company = "Amazon",
            role = "Investigation Associate, Relay Product Excellence",
            location = "Bengaluru (ADCI - A66)",
            ctc = "₹4.0 – ₹5.2 LPA",
            inHand = "₹30,500 – ₹39,000 / mo",
            perks = "+₹150/night Shift Allowance + Meal Card + Wi-Fi + Free Cab",
            portalCtc = "450000 (₹4.5 LPA)",
            difficulty = "⭐⭐ Easy-Medium",
            difficultyScore = 2,
            tier = JobTier.HIGH_PAY,
            portalName = "amazon.jobs",
            rounds = "Round 1: Versant & Logic Test -> Round 2: Risk/Investigation Case & STAR Behavioral Round",
            keywords = listOf("KYC", "AML Compliance", "Root-Cause Analysis", "SOPs", "MS Excel", "SLA", "TAT", "Escalation Resolution", "Quality Assurance"),
            fullJd = """
                Official Role Overview (Amazon Bengaluru - Job ID 10511626):
                • Investigate logistics, carrier, and transaction alerts to prevent fraud, compliance breaches, and operational risk.
                • Verify onboarding documentation (KYC/AML checks), analyze patterns across multiple data sources using MS Excel, and take timely enforcement actions based on SOPs.
                • Maintain 99%+ decision accuracy while meeting rapid Turnaround Time (TAT) targets.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "10585315",
            company = "Amazon",
            role = "Associate, Quality Services (Smart Vehicles)",
            location = "Bengaluru",
            ctc = "₹3.2 – ₹4.2 LPA",
            inHand = "₹24,500 – ₹31,500 / mo",
            perks = "Zeta Meal Card + ₹1,250 Internet + Free Cab",
            portalCtc = "360000 (₹3.6 LPA)",
            difficulty = "⭐ Very Easy",
            difficultyScore = 1,
            tier = JobTier.EASY,
            portalName = "amazon.jobs",
            rounds = "Round 1: Online English Test -> Round 2: 30-Min Behavioral Interview",
            keywords = listOf("Quality Assurance", "SOPs", "MS Excel", "SLA", "TAT", "Data Validation"),
            fullJd = """
                Official Role Overview (Amazon Bengaluru - Job ID 10585315):
                • Execute quality verification, data labeling, and audit workflows for Smart Vehicles & AI operations following strict SOP guidelines.
                • Track daily quality metrics in MS Excel and flag workflow anomalies to team leads.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "3134242",
            company = "Amazon",
            role = "ML Data Operations Associate",
            location = "Bengaluru / Virtual Hybrid",
            ctc = "₹3.2 – ₹4.0 LPA",
            inHand = "₹24,500 – ₹30,000 / mo",
            perks = "WFH Kit + ₹1,250/mo Internet + Meal Card",
            portalCtc = "350000 (₹3.5 LPA)",
            difficulty = "⭐ Very Easy",
            difficultyScore = 1,
            tier = JobTier.REMOTE,
            portalName = "amazon.jobs",
            rounds = "Round 1: Versant Test -> Round 2: 1-on-1 Operations Interview",
            keywords = listOf("Quality Assurance", "SOPs", "MS Excel", "SLA", "TAT", "Root-Cause Analysis"),
            fullJd = """
                Official Role Overview (Amazon - Job ID 3134242):
                • Analyze and audit text, image, and business datasets to train and validate Machine Learning models.
                • Maintain high precision and throughput in line with daily SLAs and SOPs.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "130410",
            company = "IBM",
            role = "Process Associate – HR Operations (EDM)",
            location = "Bengaluru (Manyata / EGL)",
            ctc = "₹3.2 – ₹4.2 LPA",
            inHand = "₹24,000 – ₹31,000 / mo",
            perks = "+₹3,500/mo Shift Allowance + Free Door-to-Door Cab",
            portalCtc = "380000 (₹3.8 LPA)",
            difficulty = "⭐ Easy",
            difficultyScore = 1,
            tier = JobTier.EASY,
            portalName = "ibm.com/careers",
            rounds = "Round 1: English Assessment -> Round 2: HR Shared Services & Excel Interview",
            keywords = listOf("Workday", "SOPs", "SLA", "TAT", "MS Excel", "VLOOKUP", "Quality Assurance", "Escalation Resolution"),
            fullJd = """
                Official Role Overview (IBM Bengaluru - Job ID 130410):
                • Manage Employee Data Management (EDM), onboarding, lifecycle changes, and HR shared services tickets in Workday and ServiceNow.
                • Audit employee records using MS Excel (VLOOKUP, Pivot Tables) to ensure 100% data integrity and SLA compliance.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "PROGEN-251785",
            company = "Infosys BPM",
            role = "Process Executive (Data / Finance Non-Voice)",
            location = "Bengaluru (Electronic City)",
            ctc = "₹2.4 – ₹3.0 LPA",
            inHand = "₹18,500 – ₹22,800 / mo",
            perks = "+₹3,000 Night Allowance + Free Cab/Bus",
            portalCtc = "280000 (₹2.8 LPA)",
            difficulty = "⭐ Fast 48h Offer",
            difficultyScore = 1,
            tier = JobTier.EASY,
            portalName = "careers.infosys.com",
            rounds = "Single-Day Walk-In: Basic Aptitude + 15-Min HR & Operations Chat",
            keywords = listOf("MS Excel", "VLOOKUP", "SOPs", "SLA", "TAT", "Invoice Processing", "Account Reconciliation"),
            fullJd = """
                Official Role Overview (Infosys BPM Bengaluru - Ref: PROGEN-HRODIRECT-251785):
                • Execute non-voice back-office data processing, financial reconciliation, and client order management.
                • Ideal immediate launchpad for BBA/B.Com freshers seeking quick corporate onboarding.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "R0000473632",
            company = "Target India",
            role = "Analyst PIM Ops (Inventory Management)",
            location = "Bengaluru (Manyata Tech Park)",
            ctc = "₹4.8 – ₹6.5 LPA",
            inHand = "₹36,500 – ₹48,500 / mo",
            perks = "10% Annual Bonus + Free Transport + Hybrid Culture",
            portalCtc = "520000 (₹5.2 LPA)",
            difficulty = "⭐⭐ Medium",
            difficultyScore = 3,
            tier = JobTier.HIGH_PAY,
            portalName = "corporate.target.com/careers",
            rounds = "Round 1: Aptitude/Excel -> Round 2: Retail Operations Case -> Round 3: Hiring Manager",
            keywords = listOf("Inventory Management", "Vendor Management", "MS Excel", "VLOOKUP", "Pivot Tables", "Power BI", "Root-Cause Analysis", "SLA"),
            fullJd = """
                Official Role Overview (Target in India Bengaluru - Job ID R0000473632):
                • Manage Product Information Management (PIM), retail merchandising data, vendor item setup, and inventory flows.
                • Use Advanced Excel, SQL basics, and BI dashboards to resolve inventory and pricing discrepancies for US stores.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "140758",
            company = "Deloitte",
            role = "Fresher Business Analyst / Associate Analyst",
            location = "Bengaluru (Ecospace, Bellandur)",
            ctc = "₹4.5 – ₹6.5 LPA",
            inHand = "₹34,500 – ₹47,500 / mo",
            perks = "₹30k Joining Bonus + ₹25k/yr Well-Being Allowance",
            portalCtc = "500000 (₹5.0 LPA)",
            difficulty = "⭐⭐⭐ Medium",
            difficultyScore = 3,
            tier = JobTier.HIGH_PAY,
            portalName = "jobsindia.deloitte.com",
            rounds = "Round 1: Versant & Analytical Test -> Round 2: Technical/Case -> Round 3: Partner Round",
            keywords = listOf("Financial Accounting", "Account Reconciliation", "MS Excel", "VLOOKUP", "Pivot Tables", "Power BI", "Root-Cause Analysis", "Stakeholder Communication"),
            fullJd = """
                Official Role Overview (Deloitte Bengaluru - Job ID 140758):
                • Support global consulting, audit, risk, and financial advisory engagements from Bengaluru.
                • Perform financial data analysis, client reporting, process mapping, and KPI dashboarding using Advanced MS Excel and Power BI.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "R-563929",
            company = "Wells Fargo",
            role = "Securities Operations Associate",
            location = "Bengaluru (Outer Ring Road)",
            ctc = "₹5.0 – ₹6.8 LPA",
            inHand = "₹38,000 – ₹50,500 / mo",
            perks = "+₹5,000/mo US Shift Allowance + Free 2-Way Cab + Free Meals",
            portalCtc = "520000 (₹5.2 LPA)",
            difficulty = "⭐⭐ Medium",
            difficultyScore = 2,
            tier = JobTier.HIGH_PAY,
            portalName = "wellsfargojobs.com",
            rounds = "Round 1: English & Finance Basics -> Round 2: Banking Operations Manager Interview",
            keywords = listOf("Account Reconciliation", "KYC", "AML Compliance", "Financial Accounting", "MS Excel", "VLOOKUP", "SLA", "TAT"),
            fullJd = """
                Official Role Overview (Wells Fargo Bengaluru - Job ID R-563929):
                • Process trade settlements, corporate actions, and securities reconciliations for institutional banking clients.
                • Investigate failed trades, Nostro/Vostro breaks, and KYC compliance flags within strict daily cut-off times.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "153561235",
            company = "Genpact",
            role = "Domain Trainee – Finance & Accounting",
            location = "Bengaluru (Bellandur)",
            ctc = "₹2.5 – ₹3.4 LPA",
            inHand = "₹19,200 – ₹25,500 / mo",
            perks = "Monthly Incentives + Free 2-Way Cab + Shift Allowance",
            portalCtc = "300000 (₹3.0 LPA)",
            difficulty = "⭐ Very Easy",
            difficultyScore = 1,
            tier = JobTier.EASY,
            portalName = "genpact.com/careers",
            rounds = "Round 1: Aptitude -> Round 2: 3 Golden Rules of Accounting & P2P/O2C Interview",
            keywords = listOf("Procure-to-Pay (P2P)", "Order-to-Cash (O2C)", "Record-to-Report (R2R)", "Account Reconciliation", "Invoice Processing", "MS Excel"),
            fullJd = """
                Official Role Overview (Genpact Bengaluru - Job ID 153561235):
                • Entry-level Domain Trainee role in Finance & Accounting (P2P, O2C, R2R).
                • Process vendor invoices, bank reconciliations, and journal entries for Fortune 500 clients.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "CLIPBOARD-REMOTE",
            company = "Clipboard Health (US)",
            role = "Billing Representative / Collections Mgr",
            location = "100% Global Remote (Work from Home)",
            ctc = "$30,000 – $50,000 / yr (~₹25–₹41 LPA)",
            inHand = "₹2,00,000 – ₹3,40,000 / mo",
            perks = "Paid in USD via Deel/Wise + 100% Work from Home",
            portalCtc = "$30,000 USD / yr",
            difficulty = "⭐⭐⭐ Written Case",
            difficultyScore = 3,
            tier = JobTier.REMOTE,
            portalName = "clipboard.com/careers",
            rounds = "Round 1: Written Billing/Excel Case Study -> Round 2: Live Video Operations Interview",
            keywords = listOf("Billing", "Account Reconciliation", "Invoice Processing", "Order-to-Cash (O2C)", "MS Excel", "VLOOKUP", "Root-Cause Analysis", "Escalation Resolution"),
            fullJd = """
                Official Role Overview (Clipboard Health - Global Remote):
                • Own end-to-end B2B invoicing, accounts receivable collections, and billing dispute resolution for US healthcare facilities.
                • Reconcile ledger balances in Excel/Spreadsheets, investigate root causes of payment delays, and maintain proactive client communication.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "NT-BLR-2026",
            company = "Northern Trust",
            role = "Associate 1 – Fund Accounting / Transfer Agency",
            location = "Bengaluru (Ecospace / Manyata)",
            ctc = "₹3.6 – ₹4.6 LPA",
            inHand = "₹27,500 – ₹34,500 / mo",
            perks = "+₹4,500 Shift Allowance + Free 2-Way Cab",
            portalCtc = "400000 (₹4.0 LPA)",
            difficulty = "⭐⭐ Easy-Medium",
            difficultyScore = 2,
            tier = JobTier.EASY,
            portalName = "careers.northerntrust.com",
            rounds = "Round 1: Accounting Basics -> Round 2: Manager Round",
            keywords = listOf("Financial Accounting", "Account Reconciliation", "Record-to-Report (R2R)", "KYC", "MS Excel", "VLOOKUP"),
            fullJd = """
                Official Role Overview (Northern Trust Bengaluru):
                • Calculate daily Net Asset Value (NAV), reconcile cash and security positions, and verify investor transactions for global asset management funds.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "REV-IND-REMOTE",
            company = "Revolut",
            role = "Operations Analyst / Support Specialist",
            location = "100% Remote (Work from anywhere in India)",
            ctc = "₹5.5 – ₹8.5 LPA",
            inHand = "₹41,500 – ₹62,000 / mo",
            perks = "100% WFH + Global FinTech Exposure",
            portalCtc = "600000 (₹6.0 LPA)",
            difficulty = "⭐⭐⭐ Medium",
            difficultyScore = 3,
            tier = JobTier.REMOTE,
            portalName = "revolut.com/careers",
            rounds = "Round 1: Analytical Test -> Round 2: Problem-Solving Interview",
            keywords = listOf("KYC", "AML Compliance", "Root-Cause Analysis", "SQL Basics", "MS Excel", "SLA", "TAT"),
            fullJd = """
                Official Role Overview (Revolut India - Remote):
                • Handle global FinTech operations, KYC/AML escalations, chargeback analysis, and process automation.
            """.trimIndent()
        ),
        VerifiedJob(
            id = "BLR-BDA-FAST",
            company = "NoBroker / Scaler / Info Edge",
            role = "Inside Sales / Business Development Associate",
            location = "Bengaluru (HSR Layout / Whitefield)",
            ctc = "₹5.0 – ₹8.0 LPA (₹4L Fixed + Incentives)",
            inHand = "₹30,000 – ₹55,000 / mo",
            perks = "Uncapped Monthly Commissions + Fast Promotion",
            portalCtc = "500000 (₹5.0 LPA)",
            difficulty = "⭐ Easy (2-Min Pitch)",
            difficultyScore = 1,
            tier = JobTier.HIGH_PAY,
            portalName = "Naukri / LinkedIn Easy Apply",
            rounds = "Round 1: 10-Min HR Call -> Round 2: 2-Minute Mock Sales Pitch",
            keywords = listOf("Stakeholder Communication", "Order-to-Cash (O2C)", "MS Excel", "Escalation Resolution", "SLA"),
            fullJd = """
                Official Role Overview (Bengaluru High-Growth Unicorns):
                • Convert inbound leads, conduct consultative product demos, manage CRM pipelines (Salesforce/LeadSquared), and drive monthly revenue targets.
            """.trimIndent()
        )
    )

    val DIRECTORY_SECTORS = listOf(
        CompanyDirectoryItem(
            sector = "Big 4 & Consulting",
            roleType = "Associate Analyst / Audit / Tax / Advisory",
            hubLocation = "Bengaluru (Bellandur/Hebbal) & Global",
            firms = listOf(
                "Deloitte (USI & India)", "EY (GDS & India)", "PwC (Acceleration Center)",
                "KPMG (Global Services)", "McKinsey & Company", "Boston Consulting Group (BCG)",
                "Bain & Company (BCN)", "Accenture", "ZS Associates", "Kearney", "Oliver Wyman",
                "Roland Berger", "Grant Thornton", "BDO", "RSM", "Protiviti", "Alvarez & Marsal",
                "FTI Consulting", "GEP Worldwide", "Mercer", "Aon Bengaluru", "WTW"
            )
        ),
        CompanyDirectoryItem(
            sector = "Investment Banks & FinTech",
            roleType = "Operations, KYC/AML, Fund Accounting & Finance Analyst",
            hubLocation = "Bengaluru (ORR/Whitefield) & Global",
            firms = listOf(
                "JPMorganChase", "Goldman Sachs Bengaluru", "Morgan Stanley", "Deutsche Bank Bengaluru",
                "Citigroup (Citi)", "Bank of America", "Barclays", "HSBC", "Standard Chartered GBS",
                "Wells Fargo Bengaluru", "Societe Generale Bengaluru", "BNP Paribas", "UBS",
                "NatWest Group", "ANZ Bank Bengaluru", "Northern Trust Bengaluru", "State Street Bengaluru",
                "Fidelity Investments", "BlackRock", "BNY Mellon", "American Express", "Visa Bengaluru",
                "Mastercard", "LSEG (London Stock Exchange Bengaluru)", "S&P Global", "Moody's",
                "CRISIL", "Morningstar", "Bloomberg", "FactSet", "HDFC / ICICI / Axis / Kotak", "Bajaj Finserv & Muthoot"
            )
        ),
        CompanyDirectoryItem(
            sector = "Big Tech & IT BPM",
            roleType = "Catalog, Risk, Finance Ops, HR Ops & Inside Sales (SDR)",
            hubLocation = "Bengaluru & Worldwide",
            firms = listOf(
                "Google (gTech & Account Strategist)", "Apple Bengaluru (Business Systems)", "Microsoft (Aspire Business)",
                "Amazon Bengaluru", "Meta", "Oracle Bengaluru (Class Of SDR)", "Salesforce Futureforce",
                "SAP Academy", "Cisco (CSAP Bengaluru)", "IBM Bengaluru", "Dell Technologies", "HP & HPE Bengaluru",
                "Lenovo", "Adobe", "ServiceNow", "Atlassian", "HubSpot", "Zoho", "Infosys BPM", "TCS BPS",
                "Wipro DOP", "Cognizant", "HCLTech", "Tech Mahindra", "Genpact", "EXL Service", "WNS Global",
                "Mu Sigma Bengaluru", "Fractal & Tiger Analytics", "Concentrix, Sutherland & Sagility", "ADP"
            )
        ),
        CompanyDirectoryItem(
            sector = "FMCG & Retail GCCs",
            roleType = "Management Trainee, Merchandising, PIM & Supply Chain",
            hubLocation = "Bengaluru GCCs & Global",
            firms = listOf(
                "Unilever (UFLP)", "Procter & Gamble (P&G)", "L'Oréal (SeedZ MT)", "Nestlé (Needs YOUth)",
                "PepsiCo", "Coca-Cola", "AB InBev GCC Bengaluru", "Diageo Business Services Bengaluru",
                "Mondelez", "Mars", "Reckitt & Colgate", "ITC, Marico, Dabur & Britannia Bengaluru",
                "Target in India (Bengaluru)", "Tesco Business Services (Bengaluru)", "Lowe's India (Bengaluru)",
                "Walmart & Flipkart (Bengaluru HQ)", "IKEA Bengaluru", "UNIQLO Global Management",
                "Zara (Inditex) & H&M", "Nike GCC Bengaluru", "Puma India HQ (Bengaluru)",
                "Decathlon India HQ (Bengaluru)", "Tata Starbucks", "Titan Company HQ (Bengaluru)", "DMart & Reliance Retail"
            )
        ),
        CompanyDirectoryItem(
            sector = "Logistics, Energy & Industrial",
            roleType = "Procurement, Finance, Logistics & Commercial Trainee",
            hubLocation = "Bengaluru & 200+ Countries",
            firms = listOf(
                "DHL Global Trainee", "Maersk Bengaluru", "DP World", "FedEx, UPS & Blue Dart",
                "Kuehne+Nagel", "Shell Business Operations (Bengaluru)", "ExxonMobil Business Center (Bengaluru)",
                "Chevron ENGINE (Bengaluru)", "BP", "Siemens Bengaluru", "GE Aerospace & GE Vernova (Bengaluru)",
                "Schneider Electric (Bengaluru)", "ABB & Hitachi Energy (Bengaluru)", "Bosch India HQ (Bengaluru)",
                "Volvo Group & Mercedes-Benz (Bengaluru)", "Honeywell, 3M & Caterpillar", "ISS World Management Trainee",
                "Tata Group, Birla, Mahindra, Reliance & L&T"
            )
        ),
        CompanyDirectoryItem(
            sector = "Unicorns & High-Growth Startups",
            roleType = "BDA, Founder's Office, Category Ops & Growth",
            hubLocation = "Bengaluru (HSR/Koramangala) & Global",
            firms = listOf(
                "Razorpay", "Zerodha", "Groww", "PhonePe", "CRED", "Slice & Navi", "Paytm & Pine Labs",
                "Revolut", "Stripe & Wise", "Swiggy & Instamart", "Zomato & Blinkit", "Zepto", "Meesho",
                "BigBasket, Myntra & Nykaa", "Uber & Ola", "Freshworks & Chargebee",
                "Whatfix, MoEngage & LeadSquared", "Darwinbox, Postman & BrowserStack",
                "NoBroker & Livspace", "Scaler, UpGrad & PhysicsWallah"
            )
        ),
        CompanyDirectoryItem(
            sector = "Media, Ad Agencies & Research",
            roleType = "Brand Executive, Media Planner & Market Research Analyst",
            hubLocation = "Bengaluru, Mumbai & Global",
            firms = listOf(
                "WPP (Ogilvy, GroupM, Mindshare, Wavemaker)", "Publicis Groupe (Leo Burnett, Publicis Sapient)",
                "Omnicom (BBDO, DDB, OMD)", "IPG (McCann, MullenLowe Lintas)", "Dentsu", "Havas",
                "Edelman & Weber Shandwick", "Gartner", "NielsenIQ (NIQ)", "Kantar", "Ipsos",
                "Euromonitor International (Bengaluru)", "Markelytics Solutions (Bengaluru)"
            )
        ),
        CompanyDirectoryItem(
            sector = "100% Global Remote (USD/EUR)",
            roleType = "Remote Billing, Operations, SDR & Executive Support",
            hubLocation = "100% Work From Home Worldwide",
            firms = listOf(
                "Clipboard Health ($30k–$50k/yr)", "Canonical (Ubuntu Junior PM/Sales/Ops)", "Deel",
                "Remote.com", "Oyster HR", "GitLab", "Automattic", "Zapier", "Toptal, Turing & Crossover",
                "Hubstaff, Doist & Buffer", "Y Combinator Startups (workatastartup.com)", "Wellfound Global Remote Startups"
            )
        ),
        CompanyDirectoryItem(
            sector = "Dubai & Middle East Direct",
            roleType = "Retail MT, Real Estate Wealth Advisor, Logistics & Airline Ops",
            hubLocation = "Dubai, Abu Dhabi, Doha & Riyadh",
            firms = listOf(
                "Emaar Properties", "DAMAC Properties", "Sobha Realty (Dubai + Bengaluru HQ)",
                "Alshaya Group", "Landmark Group (Dubai + Landmark Digital Bengaluru)",
                "Majid Al Futtaim", "Chalhoub Group & Apparel Group", "Lulu Group International",
                "Emirates Group, Qatar Airways & Etihad", "Careem & Noon.com"
            )
        )
    )

    val MOCK_QUESTIONS = listOf(
        MockQuestionItem(
            question = "Tell me about yourself. (60-Second Corporate Pitch)",
            category = "HR / Opening",
            winningScript = "Hi, thank you for this opportunity. I am a BBA graduate with a strong foundation in business operations, financial reconciliation, and data analysis. During my academic projects, I focused heavily on practical tools like Advanced MS Excel—specifically VLOOKUP, Pivot Tables, and data validation—to organize large datasets and solve operational bottlenecks. I thrive in structured, fast-paced environments where accuracy, following SOPs, and meeting turnaround times matter. That is why I am excited about this role, where I can contribute strong analytical accuracy and grow with your team from day one."
        ),
        MockQuestionItem(
            question = "What are the 3 Golden Rules of Accounting & P2P / O2C / R2R?",
            category = "Technical Finance / Operations",
            winningScript = "1. Real Account: Debit what comes in, Credit what goes out.\n2. Personal Account: Debit the receiver, Credit the giver.\n3. Nominal Account: Debit all expenses and losses, Credit all incomes and gains.\n\n• P2P (Procure-to-Pay): The purchasing cycle from Purchase Requisition and PO to 3-way Invoice Matching and Vendor Payment.\n• O2C (Order-to-Cash): Customer order entry, fulfillment, invoicing, and cash collections.\n• R2R (Record-to-Report): Recording financial entries, bank reconciliations, and preparing monthly financial statements."
        ),
        MockQuestionItem(
            question = "Tell me about a time you solved a data mismatch under a tight deadline (STAR).",
            category = "Behavioral (STAR Method)",
            winningScript = "(Situation) During my final-year project, our team had to audit 3 years of financial statements for 5 firms, but two days before the deadline, we found duplicate ledger entries causing balance mismatches.\n(Task) I needed to audit 1,000+ data rows and fix the report within 24 hours.\n(Action) Instead of manual checks, I applied Excel Conditional Formatting and VLOOKUP to isolate discrepancies in 45 minutes, divided verification tasks among my teammates, and reconciled the final Pivot tables.\n(Result) We submitted a 100% reconciled report 4 hours ahead of deadline and earned the top grade."
        ),
        MockQuestionItem(
            question = "How do you use VLOOKUP and Pivot Tables in Excel?",
            category = "Technical Tools",
            winningScript = "I use VLOOKUP (or XLOOKUP) to match and extract data across different sheets using a unique key such as Invoice ID or Employee Code: =VLOOKUP(lookup_value, table_array, col_index_num, FALSE). I use Pivot Tables to aggregate thousands of raw transaction rows into actionable summaries—grouping by date, vendor, or category to calculate sums, averages, and variances in seconds without manual calculation errors."
        )
    )

    val VERSANT_SENTENCES = listOf(
        "The quarterly financial reconciliation report was submitted to the procurement manager ahead of Tuesday's deadline.",
        "Please ensure all vendor purchase orders match the warehouse receiving documentation before processing payment.",
        "Customer satisfaction increased by twenty percent after the operations team streamlined the escalation protocol.",
        "The automated dashboard updates daily transaction summaries to help supervisors track turnaround time."
    )
}
