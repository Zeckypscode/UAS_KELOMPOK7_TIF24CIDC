# SiapSen — Aplikasi Absensi Karyawan

## Deskripsi Project
SiapSen adalah aplikasi absensi karyawan berbasis **Android Native (Kotlin)**.
Aplikasi ini memungkinkan pengguna melakukan absen masuk/keluar dengan bukti
foto dan lokasi otomatis, mengajukan izin/cuti, serta melihat riwayat
absensi dan pengajuan — sesuai ketentuan UAS Pemrograman Mobile 1.

### Fitur Utama
- ✅ **Absen Masuk & Absen Keluar** — satu sentuhan tombol, lengkap bukti foto & lokasi
- ✅ **Perizinan & Pengajuan Cuti** — form dengan tanggal, alasan, dan lampiran foto dokumen
- ✅ **Riwayat Absen** — riwayat absensi & izin/cuti dalam satu RecyclerView terurut waktu
- ✅ **Lokasi Otomatis** — FusedLocationProviderClient + reverse geocoding alamat
- ✅ **Kamera** — Intent `ACTION_IMAGE_CAPTURE` untuk bukti foto absensi/lampiran

### Komponen Android Native yang digunakan
`Activity`, `Fragment`, `RecyclerView`, `Intent`, Navigation Component (Safe Args), Room Database.

## Daftar Anggota
| Nama | NPM | Peran |
|---|---|---|
| _(Muhammad Daffa S.F.P.N)_ | _(24552011326)_ | Project Lead / UI |
| _(Moch Zaky Akbar | _(24552011323)_ | Database & Lokasi |
| _(Rizky Maulana)_ | _(24552011345)_ | Kamera & Izin/Cuti |

> Lengkapi tabel di atas dengan nama dan NPM asli seluruh anggota kelompok sebelum submit.

## Link Video Penjelasan
_(tempel link YouTube/Google Drive video demo & penjelasan kode di sini — pastikan akses "dapat dilihat oleh siapa saja")_

## Screenshot Aplikasi
_(tempel minimal 2 screenshot tampilan aplikasi di sini, contoh:)_

| Beranda | Absen Masuk |
|---|---|
| ![home](docs/screenshot_home.png) | ![absen](docs/screenshot_absen.png) |

## Cara Menjalankan Project

1. Clone repository:
   ```bash
   git clone <link-repository-kelompok>
   ```
2. Buka folder project di **Android Studio** (Hedgehog atau lebih baru).
3. Tunggu proses **Gradle Sync** selesai (Android Studio otomatis melengkapi Gradle Wrapper).
4. Sambungkan perangkat Android fisik atau jalankan emulator (disarankan API 26+).
5. Klik **Run ▶** untuk menjalankan aplikasi.
6. Berikan izin **Kamera** dan **Lokasi** saat diminta agar seluruh fitur dapat berfungsi.

### Build APK Release
```
Build > Generate Signed Bundle / APK > APK > release
```
Salin hasil `app-release.apk` ke folder `apk/` pada repository sesuai struktur wajib.

## Struktur Project
```
SiapsenApp/
├── app/
│   └── src/main/
│       ├── java/com/example/siapsen/
│       │   ├── MainActivity.kt
│       │   ├── data/            (Room: Entity, DAO, Database)
│       │   ├── ui/home/         (Beranda)
│       │   ├── ui/absen/        (Absen Masuk/Keluar)
│       │   ├── ui/izin/         (Izin/Cuti)
│       │   ├── ui/riwayat/      (Riwayat + RecyclerView Adapter)
│       │   └── utils/           (LocationHelper, ImageUtils)
│       └── res/                 (layout, navigation, drawable, values)
├── docs/
│   └── Laporan_OOAD_SiapSen.md
├── apk/
│   └── app-release.apk          (tambahkan setelah build release)
└── README.md
```

## Ketentuan UAS yang Dipenuhi
- Platform: **Android Native Kotlin** (bukan Flutter/React Native)
- Komponen wajib: **Activity, Fragment, RecyclerView, Intent** ✅
- Database lokal: **Room** ✅
- Aplikasi stabil, seluruh fitur utama berjalan sesuai rencana ✅
