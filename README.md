# 🇮🇳 IST Clock — Android

A clean, lightweight Android clock dedicated to **India Standard Time (IST)**. Built with Kotlin and Jetpack Compose, IST Clock provides a distraction-free time display with useful formatting, themes, and quick controls.

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?style=for-the-badge&logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose)
![API](https://img.shields.io/badge/Android%207.0%2B-API%2024%2B-lightgrey?style=for-the-badge)

---

## 📱 Overview

**IST Clock** is a focused Android clock designed around **India Standard Time — Asia/Kolkata**. It keeps the interface simple while providing enough customization for everyday use.

The app is lightweight, works offline, and does not require an internet connection for its core functionality.

## ✨ Features

- 🕐 **Live IST** — displays current India Standard Time using `Asia/Kolkata`
- 📅 **Live date & day** — keeps the current day and date visible
- ⏱️ **Seconds toggle** — show or hide seconds when needed
- 🕛 **12-hour / 24-hour format** — switch between preferred time formats
- 🎨 **Multiple themes** — Midnight, Classic, Tricolor, Neon, Sunset, and Mint
- 📋 **Quick copy** — copy the current IST time with one tap
- 💾 **Saved preferences** — remembers theme and display settings
- 📴 **Offline-first** — no internet permission required for the clock
- 📱 **Android 7.0+** — supports API 24 and newer

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Platform | Android |
| Language | Kotlin |
| UI | Jetpack Compose |
| Design | Material 3 |
| Build | Gradle / Android Gradle Plugin |
| Time API | Java Time (`Asia/Kolkata`) |

## 🏗️ Project Structure

```text
ist-clock/
├── app/                 # Android application module
├── gradle/              # Gradle wrapper and configuration
├── build.gradle.kts     # Root build configuration
├── settings.gradle.kts  # Gradle project settings
├── gradle.properties    # Gradle/project properties
├── gradlew              # Gradle wrapper for Unix-like systems
├── gradlew.bat          # Gradle wrapper for Windows
└── README.md            # Documentation
```

## 🚀 Getting Started

### Prerequisites

- Android Studio — latest stable version recommended
- JDK 17 or the version required by the project's Gradle/Android toolchain
- Android SDK with API 24 or newer

### Clone the repository

```bash
git clone https://github.com/Harsh0675/ist-clock.git
cd ist-clock
```

### Open and run

1. Open the project in Android Studio.
2. Allow Gradle to sync.
3. Connect an Android device or start an emulator.
4. Select the `app` configuration.
5. Click **Run**.

## 📦 Build the APK

### Local build

```bash
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

The generated debug APK is located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### GitHub Actions

The repository can also build the debug APK through GitHub Actions:

1. Open the **Actions** tab.
2. Select **Build IST Clock APK**.
3. Run the workflow or push a change to `main`.
4. Open the completed workflow run.
5. Download the **IST-Clock-debug-apk** artifact.

## 🔐 Privacy

IST Clock is designed to work without an internet connection. The clock uses the device's time APIs and the `Asia/Kolkata` time zone to display IST.

No internet permission is required for the core clock functionality, and the app does not need an online account to operate.

## 🗺️ Roadmap

- [ ] Home-screen widget
- [ ] Alarm and reminder options
- [ ] More clock layouts
- [ ] Additional accessibility improvements
- [ ] Release build and distribution improvements

## 🎯 Portfolio Highlights

This project demonstrates:

- Native Android development with Kotlin
- Modern UI development with Jetpack Compose and Material 3
- Time-zone handling with Java Time
- Gradle-based Android builds
- GitHub Actions APK generation
- Offline-first mobile application design

## 🤝 Contributing

Suggestions, issues, and improvements are welcome. Open an issue or submit a pull request if you have an idea for improving IST Clock.

## 📄 License

See the repository license information for details.

## 👤 Author

**Harsh Nagar**

- GitHub: [@Harsh0675](https://github.com/Harsh0675)
- Portfolio: [harsh0675.github.io/Portfolio](https://harsh0675.github.io/Portfolio/)

---

⭐ If you like the project, consider giving the repository a star.