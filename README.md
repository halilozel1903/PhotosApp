# Photos 🖼 App 📱

![Screenshot](https://github.com/halilozel1903/PhotosApp/blob/master/flickr.jpg)

This is an application developed using the `Flickr` API.

You can review the document. 📝

https://www.flickr.com/services/developer/api/

I show the last pictures that people have uploaded on the screen. `20` images are uploaded per request. Service request is made as you go down.

Clicking on the pictures opens the detail page. The pictures on the detail page are shown in their actual size.

API used in Home Page: https://www.flickr.com/services/api/explore/flickr.photos.getRecent

This API takes pictures on the Home Page: https://www.flickr.com/services/api/explore/flickr.photos.getInfo

## App Screenshots 📸

<img src="https://github.com/halilozel1903/PhotosApp/blob/master/home.jpeg" width="350" /> <img src="https://github.com/halilozel1903/PhotosApp/blob/master/home1.jpeg" width="350" />

<img src="https://github.com/halilozel1903/PhotosApp/blob/master/detail.jpeg" width="350" /> <img src="https://github.com/halilozel1903/PhotosApp/blob/master/detail1.jpeg" width="350" />  

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

## Technology Stack 🛠

- **Android Gradle Plugin**: 8.6.1
- **Gradle**: 8.9
- **Compile SDK**: 35
- **Min SDK**: 21
- **Target SDK**: 35
- **Java**: 17
- **Architecture**: MVVM with Repository Pattern
- **DI**: Hilt 2.52
- **Networking**: Retrofit 2.11.0, OkHttp 4.12.0
- **Image Loading**: Glide 4.16.0
- **UI**: Material Design 3, ViewBinding, Navigation Component
- **Async**: Kotlin Coroutines 1.9.0
- **Testing**: JUnit, Espresso

If this project help 💁 you to develop, you can give me a cup of coffee. ☕

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/halilozel1903)

## License ℹ️
```
MIT License

Copyright (c) 2023 Halil OZEL

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
