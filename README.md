# Photos 🖼 App 📱

[![Android CI Build](https://github.com/halilozel1903/PhotosApp/actions/workflows/android-build.yml/badge.svg)](https://github.com/halilozel1903/PhotosApp/actions/workflows/android-build.yml)

![Screenshot](https://github.com/halilozel1903/PhotosApp/blob/master/flickr.jpg)

## About the App 📖

This is a modern Android application that displays recently uploaded photos from the **Flickr API**. The app is built with clean architecture principles and follows Android best practices.

### Key Features ✨

- 📷 **Browse Recent Photos**: View the latest photos uploaded by Flickr users
- ♾️ **Infinite Scrolling**: Automatic pagination loads 20 photos at a time as you scroll
- ⭐ **Favorites Management**: Save your favorite photos for offline viewing
- 🔍 **Photo Details**: View photos in full resolution with detailed information
- 🌐 **Multi-language Support**: Full support for English and Turkish languages
- 💾 **Offline Storage**: Favorite photos are stored locally for offline access
- 🎨 **Material Design 3**: Modern UI following Material Design guidelines
- 🔄 **Pull to Refresh**: Refresh the photo list with a simple pull gesture

## Flickr API Documentation 📝

The app uses the following Flickr API endpoints:

- **Recent Photos**: [flickr.photos.getRecent](https://www.flickr.com/services/api/explore/flickr.photos.getRecent)
- **Photo Info**: [flickr.photos.getInfo](https://www.flickr.com/services/api/explore/flickr.photos.getInfo)

For more information, visit the [Flickr Developer API](https://www.flickr.com/services/developer/api/)

## App Screenshots 📸

<img src="https://github.com/halilozel1903/PhotosApp/blob/master/home.jpeg" width="350" /> <img src="https://github.com/halilozel1903/PhotosApp/blob/master/home1.jpeg" width="350" />

<img src="https://github.com/halilozel1903/PhotosApp/blob/master/detail.jpeg" width="350" /> <img src="https://github.com/halilozel1903/PhotosApp/blob/master/detail1.jpeg" width="350" />  

## Technology Stack 🛠

### Core Technologies
- **Language**: Java 17
- **Build System**: Gradle 8.9
- **Android Gradle Plugin**: 8.6.1
- **Min SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

### Architecture & Design Patterns
- **Architecture**: MVVM (Model-View-ViewModel)
- **Design Pattern**: Repository Pattern
- **Dependency Injection**: Hilt 2.52
- **View Binding**: Enabled for type-safe view access

### Networking
- **HTTP Client**: Retrofit 2.11.0
- **JSON Parsing**: Gson Converter 2.11.0
- **Logging**: OkHttp Logging Interceptor 4.12.0

### UI & UX
- **Material Design**: 1.12.0
- **Image Loading**: Glide 4.16.0
- **Navigation**: Navigation Component 2.8.5
- **Layouts**: ConstraintLayout, RecyclerView, CardView, SwipeRefreshLayout

### Asynchronous Operations
- **Coroutines**: Kotlin Coroutines 1.9.0
- **Lifecycle**: AndroidX Lifecycle 2.8.7
- **LiveData**: For observable data holders

### Data Persistence
- **Local Storage**: SharedPreferences with Gson serialization
- **Favorites**: Offline favorites management

### Testing
- **Unit Testing**: JUnit 4.13.2
- **UI Testing**: Espresso 3.6.1
- **Test Runner**: AndroidX Test 1.2.1

## Architecture Overview 🏗️

The app follows **MVVM architecture** with these components:

```
├── UI Layer (Activities & Fragments)
│   ├── MainActivity - Navigation host
│   ├── PhotosListFragment - Browse photos
│   ├── PhotoDetailFragment - View photo details
│   └── FavoritesFragment - Manage favorites
│
├── ViewModel Layer
│   └── PhotoViewModel - Business logic & state management
│
├── Repository Layer
│   ├── PhotoRepository - Data operations
│   └── FavoritesManager - Local favorites storage
│
├── Data Layer
│   ├── Network (Retrofit/API)
│   └── Local (SharedPreferences)
│
└── DI Layer (Hilt Modules)
    ├── NetworkModule - Network dependencies
    └── ApiModule - API service
```

## Build & Verify 🔧

### Requirements
- JDK 17 or higher
- Android SDK with API Level 35
- Gradle 8.9 (wrapper included)

### Building the Project
```bash
# Clean and build the project
./gradlew clean build

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease
```

### Build Verification Tasks
This project includes custom Gradle tasks to verify build health:

```bash
# Run build health check
./gradlew buildHealthCheck

# Verify all dependencies can be resolved
./gradlew verifyDependencies

# Run full build verification (includes lint, tests, and build)
./gradlew fullBuildVerification
```

### Running Tests
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Continuous Integration
The project includes GitHub Actions workflow that automatically:
- ✅ Builds the project on every push/PR
- ✅ Runs lint checks
- ✅ Executes tests
- ✅ Uploads build reports and artifacts

Check `.github/workflows/android-build.yml` for CI/CD configuration.

## Code Quality & Safety 🛡️

### Implemented Safety Measures
- **Null Safety**: Comprehensive null checks throughout the codebase
- **Memory Leak Prevention**: Proper lifecycle management and observer cleanup
- **Error Handling**: Try-catch blocks for critical operations
- **Input Validation**: Parameter validation before processing
- **Exception Recovery**: Graceful degradation on errors

### Best Practices
- **View Binding**: Type-safe view access without findViewById
- **LiveData**: Lifecycle-aware observable data
- **Coroutines**: Structured concurrency for async operations
- **Dependency Injection**: Loose coupling with Hilt
- **Material Design**: Consistent and modern UI/UX

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request.

## License ℹ️

```
MIT License

Copyright (c) 2025 Halil OZEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Support ☕

If this project helps you, you can give me a cup of coffee ☕

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/halilozel1903)

---

**Developed with ❤️ by [Halil OZEL](https://github.com/halilozel1903)**
