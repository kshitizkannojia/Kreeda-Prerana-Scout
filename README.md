# Kreeda-Prerana Scout

**A grassroots sports talent tracker for rural school physical education teachers.**

Kreeda-Prerana Scout enables PE teachers in rural Indian schools to record, monitor, and identify student athlete potential — completely offline.

## Features

- **Precision Stopwatch** — 10ms accuracy (2 decimal places) using `SystemClock.elapsedRealtime()`
- **Batch Entry** — Record performance data for 30 students in a single session
- **Milestone Badges** — Automatic "District Level Ready" badge on crossing sport benchmarks
- **Talent Curve** — Line graph showing performance improvement over time (MPAndroidChart)
- **Leaderboard** — School-level ranking sorted by best scores per sport category
- **13 Sport Events** — Athletics, Kabaddi, Kho-Kho, Wrestling, and more
- **100% Offline** — Room Database (SQLite) for complete offline functionality

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |
| Database | Room (SQLite) |
| UI | Material Design Components + XML Layouts |
| Charts | MPAndroidChart v3.1.0 |
| Architecture | MVVM |
| Threading | Executors + Handler |

## Project Structure

```
app/src/main/java/com/kreeda/preranascout/
├── KreedaApp.java
├── data/
│   ├── dao/        (AthleteDao, TrialRecordDao)
│   ├── database/   (AppDatabase)
│   └── entity/     (Athlete, TrialRecord)
├── ui/
│   ├── main/       (MainActivity, SplashActivity)
│   ├── athlete/    (AddAthleteActivity, AthleteDetailActivity)
│   ├── trial/      (TrialLoggerActivity, BatchEntryActivity)
│   ├── leaderboard/(LeaderboardActivity)
│   └── talentcurve/(TalentCurveActivity)
└── utils/          (BenchmarkUtils)
```

## Building

1. Open in Android Studio
2. Sync Gradle
3. Run on device/emulator (API 24+)

## Author

**Kshitiz Kannojia** (1ME22CS066)  
MS Engineering College, Bengaluru  
VTU Internship Project 2025-26
