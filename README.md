# 🇮🇳 IST Clock — Android

A polished Android clock focused on **India Standard Time (IST)** with a clean Material 3 interface, live updates, themes and useful quick controls.

## ✨ Features

- 🕐 Live India Standard Time — Asia/Kolkata
- 📅 Live day and date
- 🌙 Midnight, Classic, Tricolor, Neon, Sunset and Mint themes
- ⏱️ Toggle seconds on/off
- 🕛 Switch between 12-hour and 24-hour formats
- 📋 Copy the current IST time with one tap
- 💾 Remembers your theme and display preferences
- 📴 Works offline — no internet permission required
- 📱 Android 7.0+ (API 24+)

## 📦 Build the APK

### GitHub Actions

1. Open **Actions** in this repository.
2. Select **Build IST Clock APK**.
3. Choose **Run workflow** or push a change to `main`.
4. Open the completed workflow run.
5. Download **IST-Clock-debug-apk** from Artifacts.

### Local build

```bash
./gradlew assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 🛠️ Tech stack

- Kotlin
- Jetpack Compose
- Material 3
- Android Gradle Plugin
- Java Time (`Asia/Kolkata`)

## 🔐 Privacy

IST Clock does not need an internet connection and does not collect or transmit your clock preferences.

## 📄 License

Use and modify this project for learning and personal development.
