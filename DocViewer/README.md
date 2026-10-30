# Документы: просмотрщик документов для Android

Этап 1 из 9: каркас проекта, Gradle, дизайн-система (тема), контракты движков, иконка.

## Сборка
1. Установите Android Studio (актуальная стабильная) и JDK 17.
2. File → Open → папка проекта. Дождитесь Gradle Sync.
3. Если нет gradle-wrapper.jar: Android Studio предложит его создать, либо выполните `gradle wrapper --gradle-version 8.10.2`.
4. Отладочный APK: `./gradlew assembleDebug` → `app/build/outputs/apk/debug/`.
5. Release APK (подписан debug-ключом для личного использования): `./gradlew assembleRelease`.

## Установка на телефон
Включите «Отладку по USB», подключите телефон и нажмите Run в Android Studio, либо скопируйте APK и установите вручную.

Минимальная версия Android: 10 (API 29). Язык интерфейса: только русский.
