<h1 align="center"><b>🌡️ TempConv</b></h1>

**TempConv** adalah aplikasi Android berbasis **Kotlin dan Jetpack Compose** untuk mengonversi input **Celsius (°C)** menjadi **Fahrenheit (°F)**, **Kelvin (K)**, dan **Reaumur (°R)**.

---

## 🚀 Fitur Utama

- 🔁 Konversi Celsius ke Fahrenheit, Kelvin, dan Reaumur secara real-time
- 🧮 Input dan Output Otomatis
- 🌙 Dukungan Mode Gelap (Dark Mode)
- 📱 Tampilan UI yang sederhana, intuitif, dan user-friendly
- ↩️ Tombol reset untuk mengosongkan input dan hasil

---

## 🧠 Teknologi yang Digunakan

| Tool / Library        | Deskripsi                             |
|-----------------------|----------------------------------------|
| Kotlin                | Bahasa pemrograman utama (Android)     |
| Android Studio        | IDE untuk pengembangan aplikasi        |
| Jetpack Compose       | Membangun antarmuka deklaratif          |
| Material 3            | Komponen dan tema antarmuka             |

---

## 🗂️ Struktur Branch

| Branch             | Deskripsi                                |
|--------------------|-------------------------------------------|
| `master`           | Branch utama (versi stabil)               |
| `feature/iqbal`   | Pengembangan fitur dari Iqbaal           |
| `feature/jey`      | Pengembangan fitur dari Jey              |
| `feature/ngawi`    | Pengembangan fitur dari Ngawi            |

---

## ⚙️ Cara Menjalankan Aplikasi

Prasyarat: Android Studio, **JDK 17** untuk Gradle, Android SDK Platform **34**, serta emulator atau perangkat Android **6.0 (API 23)** ke atas.

1. Clone repositori ini:
   ```bash
   git clone https://github.com/RizqyNurfauzella/TempConv.git
   cd TempConv
   ```
2. Buka folder `TempConv` di Android Studio.
3. Pilih JDK 17 sebagai Gradle JDK, pasang SDK Platform 34 jika diminta, lalu jalankan Gradle Sync.
4. Pilih emulator atau perangkat yang terhubung, kemudian jalankan modul `app`.

Untuk membangun APK debug dari terminal:

**Windows (PowerShell):**
```powershell
.\gradlew.bat :app:assembleDebug
```

**Linux/macOS:**
```bash
bash gradlew :app:assembleDebug
```

APK hasil build berada di `app/build/outputs/apk/debug/app-debug.apk`.

## ✅ Pemeriksaan Otomatis

[![Android build](https://github.com/RizqyNurfauzella/TempConv/actions/workflows/android-build.yml/badge.svg)](https://github.com/RizqyNurfauzella/TempConv/actions/workflows/android-build.yml)

Pull request menjalankan unit test konversi dan build APK debug. Pengujian konversi mencakup 0 °C, 100 °C, -40 °C, dan -273,15 °C.

## 👥 Tim Pengembang

| NIM        | Nama                                 | Username Github |
|------------|--------------------------------------|-----------------|
| 6706220110 | Iqbaal Hibatullooh                   | iqbaalhibatulloh    
| 6706220088 | Muhammad Ihsan Firjatulloh Al Khasaf | mihsnfaa    
| 6706223074 | Rizqy Nurfauzella  | RizqyNurfauzella
