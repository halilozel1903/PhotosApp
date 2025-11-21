# Quick Start Guide 🚀

## Hızlı Başlangıç / Quick Setup

### 1. Gereksinimler / Prerequisites
```bash
✅ JDK 17 veya üzeri
✅ Android SDK (API 35)
✅ Git
```

### 2. Projeyi İndirin / Clone Repository
```bash
git clone https://github.com/halilozel1903/PhotosApp.git
cd PhotosApp
```

### 3. Build Sağlığını Kontrol Edin / Check Build Health
```bash
./gradlew buildHealthCheck
```

Çıktıda şunları görmelisiniz:
```
========================================
🏥 Build Health Check Report
========================================
✅ Gradle Version: 8.9
✅ Android Gradle Plugin: Configured
✅ Repositories configured: X
✅ Build configuration looks healthy!
=========================================
```

### 4. Projeyi Build Edin / Build Project
```bash
# İlk build (tüm bağımlılıkları indirir)
./gradlew clean build

# Debug APK oluştur
./gradlew assembleDebug
```

### 5. Testleri Çalıştırın / Run Tests
```bash
./gradlew test
```

## Geliştirme / Development

### IDE Kurulumu / IDE Setup
1. Android Studio'yu açın
2. "Open an Existing Project" seçin
3. PhotosApp klasörünü seçin
4. Gradle sync tamamlanmasını bekleyin

### Kod Değişikliği Yapmadan Önce / Before Making Changes
```bash
# 1. En son kodu çekin
git pull origin master

# 2. Yeni branch oluşturun
git checkout -b feature/your-feature-name

# 3. Build'in çalıştığından emin olun
./gradlew buildHealthCheck
```

### Kod Değişikliği Yaptıktan Sonra / After Making Changes
```bash
# 1. Build'i test edin
./gradlew clean assembleDebug

# 2. Testleri çalıştırın
./gradlew test

# 3. Tam doğrulama yapın
./gradlew fullBuildVerification
```

## Sorun Giderme / Troubleshooting

### "Build failed" hatası alıyorsanız:

#### Adım 1: Gradle cache temizliği
```bash
./gradlew clean
rm -rf .gradle/
./gradlew build
```

#### Adım 2: Bağımlılıkları kontrol edin
```bash
./gradlew verifyDependencies
```

#### Adım 3: Gradle wrapper'ı yenileyin
```bash
./gradlew wrapper --gradle-version=8.9
```

### "SDK not found" hatası alıyorsanız:

`local.properties` dosyası oluşturun:
```properties
sdk.dir=/path/to/your/Android/Sdk
```

veya ortam değişkeni ayarlayın:
```bash
export ANDROID_HOME=/path/to/your/Android/Sdk
```

### Bağımlılık çözümlenemiyor hatası:

1. İnternet bağlantınızı kontrol edin
2. Proxy ayarlarınızı kontrol edin
3. Gradle cache'i temizleyin:
```bash
rm -rf ~/.gradle/caches/
```

## Yararlı Komutlar / Useful Commands

### Build Komutları / Build Commands
```bash
# Temizle
./gradlew clean

# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Tüm varyantları build et
./gradlew assemble
```

### Test Komutları / Test Commands
```bash
# Unit testler
./gradlew test

# Instrumented testler (emulator/device gerekli)
./gradlew connectedAndroidTest

# Tüm testler
./gradlew testDebugUnitTest
```

### Kod Kalitesi / Code Quality
```bash
# Lint kontrolü
./gradlew lint

# Lint raporunu aç
open app/build/reports/lint-results.html
```

### Dependency Yönetimi / Dependency Management
```bash
# Bağımlılık ağacını göster
./gradlew dependencies

# Güncellenebilir bağımlılıkları göster
./gradlew dependencyUpdates
```

## CI/CD

### GitHub Actions
Her push ve PR'da otomatik olarak:
- Build yapılır
- Testler çalıştırılır
- Lint kontrolleri yapılır
- Raporlar oluşturulur

Durum: [![Android CI Build](https://github.com/halilozel1903/PhotosApp/actions/workflows/android-build.yml/badge.svg)](https://github.com/halilozel1903/PhotosApp/actions/workflows/android-build.yml)

## Önemli Dosyalar / Important Files

```
PhotosApp/
├── app/
│   ├── build.gradle              # App-level dependencies
│   └── src/
├── build.gradle                  # Project-level configuration
├── gradle.properties             # Gradle settings
├── settings.gradle               # Project modules
├── .github/
│   └── workflows/
│       └── android-build.yml     # CI/CD configuration
├── README.md                     # Main documentation
├── BUILD_VERIFICATION_TR.md      # Build verification guide (TR)
└── UPDATE_SUMMARY.md             # Update summary
```

## Daha Fazla Bilgi / More Information

- **Detaylı Dokümantasyon**: [README.md](README.md)
- **Build Doğrulama**: [BUILD_VERIFICATION_TR.md](BUILD_VERIFICATION_TR.md)
- **Güncelleme Özeti**: [UPDATE_SUMMARY.md](UPDATE_SUMMARY.md)
- **Changelog**: [CHANGELOG.md](CHANGELOG.md)
- **Flickr API Docs**: https://www.flickr.com/services/developer/api/

## Destek / Support

Sorularınız için:
1. Issues bölümünü kontrol edin
2. Yeni issue açın
3. Pull request gönderin

## Lisans / License
MIT License - Detaylar için [README.md](README.md) dosyasına bakın
