# SiapSen — Aplikasi Absensi Karyawan

## Deskripsi Project
Waktu jadi lebih mudah dikendalikan. Kehadiran jadi lebih mudah dibuktikan. Perkenalkan SiapSen, aplikasi absensi karyawan berbasis Android yang dibangun secara native menggunakan Kotlin, hadir untuk membuat pencatatan kehadiran secepat membuka smartphone.
Melalui kombinasi kamera dan GPS, setiap sesi absen masuk maupun pulang terverifikasi secara otomatis lewat foto dan titik lokasi real-time, sehingga proses yang dulunya memakan waktu kini selesai dalam hitungan detik. Kebutuhan izin dan cuti pun dapat diajukan langsung dari aplikasi, lengkap dengan alasan dan rentang tanggal, tanpa perlu proses administrasi yang berbelit.
Seluruh riwayat kehadiran dan status pengajuan tersaji rapi dan mudah ditelusuri kapan saja, sementara pengelolaan akun tetap sederhana lewat halaman profil. Dengan antarmuka yang bersih dan alur yang ringkas, SiapSen menghadirkan cara baru dalam mengelola waktu kehadiran bagi karyawan, dan bagi perusahaan.

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
| _(Moch Zaky Akbar_) | _(24552011323)_ | Database & Lokasi |
| _(Rizky Maulana)_ | _(24552011345)_ | Kamera & Izin/Cuti |


## Link Video Penjelasan
https://drive.google.com/drive/folders/1HZuZ8qOqCT2RFPdwf45iMnBPBUMPSfw2?usp=drive_link

## Screenshot Aplikasi
<img width="634" height="1378" alt="image" src="https://github.com/user-attachments/assets/9c0acbaa-d43f-4600-9b9d-53b1cf8edb07" />
<img width="634" height="1378" alt="image" src="https://github.com/user-attachments/assets/b31c4348-1d80-456f-a7bf-fb4e247b93ae" />
<img width="634" height="1378" alt="image" src="https://github.com/user-attachments/assets/7e356061-f2dc-4be7-99b7-115447491186" />
<img width="634" height="1378" alt="image" src="https://github.com/user-attachments/assets/0eefbc50-40b1-4cd0-b32b-a936cbcf4cc2" />
<img width="634" height="1378" alt="image" src="https://github.com/user-attachments/assets/e6d9cdbe-aa1e-462d-9e96-a700cf71567a" />



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

