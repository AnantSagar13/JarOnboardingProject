# Jar Onboarding Animation App

A modern Android onboarding application built with Jetpack Compose, featuring sequential tilting card animations and live API integration.

## 🚀 How to Run the Project

### Prerequisites
- **Android Studio**: Hedgehog | 2023.1.1 or later
- **Kotlin**: 2.0.21+
- **Android SDK**: 36 (compileSdk)
- **Minimum SDK**: 26 (Android 8.0)
- **JDK**: 17 or later

### Installation & Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/AnantSagar13/JarOnboardingProject.git
   cd OnboardingProject
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned project directory and open it

3. **Sync Dependencies**
   - Android Studio should automatically sync the project
   - If not, click "Sync Project with Gradle Files" or press `Ctrl+Shift+O`
   - Wait for all dependencies to download

4. **Run the Application**
   - Connect an Android device (API 26+) or start an emulator
   - Click the "Run" button or press `Shift+F10`
   - Select your target device
   - The app will build and install automatically

## 🔧 Dependencies & Setup Required

### No Additional Setup Required ✅
- **No API keys needed**: Uses public Jar API endpoint
- **No environment variables**: All configuration is included
- **No external services**: Self-contained application
- **Internet connection required**: For fetching live API data and images

### Key Dependencies
- **Jetpack Compose**: Modern UI toolkit
- **Retrofit + OkHttp**: Networking
- **Gson**: JSON parsing
- **Kotlin Coroutines**: Async operations
- **Coil**: Image loading
- **Lottie Compose**: Animations
- **Material Design 3**: UI components

## 📱 Implementation Overview

### Key Features
- **Sequential Tilting Animation**: Cards appear with alternating +6°/-6° tilts for dynamic visual flow
- **Live API Integration**: All content, timing, and colors fetched from `https://myjar.app/_assets/shared/education-metadata.json`
- **State Preservation**: Maintains card expansion state during navigation between screens
- **System Back Gesture**: Proper navigation flow (restart animation vs return to previous screen)
- **Dynamic Visual Effects**: Radial gradient background, real-time color transitions, Lottie animations

### Architecture
- **MVVM Pattern**: Clean separation with ViewModel and Repository
- **Jetpack Compose**: Declarative UI with smooth animations
- **Coroutines + StateFlow**: Reactive data flow and async operations
- **Clean Architecture**: Proper layering (data, domain, presentation)

### Animation Flow
1. **Welcome Screen** → "Welcome to INSTANT SAVING" display
2. **Sequential Cards** → Cards appear with tilting animations and slide up from bottom
3. **Interactive State** → Tap cards to expand/collapse with preserved state
4. **Navigation** → CTA button leads to landing page with proper back navigation

### Technical Highlights
- **API-Driven Timing**: All animation intervals controlled by live API response
- **Pixel-Perfect Design**: Exact Figma implementation with precise positioning
- **Performance Optimized**: Smooth 60fps animations with minimal recompositions
- **Modern Android Standards**: Latest Compose, Kotlin 2.0, Material Design 3

---

**Developer**: Anant Sagar  
**Assignment**: Jar Onboarding Implementation  
**Focus**: Modern Android development with clean architecture and smooth animations
