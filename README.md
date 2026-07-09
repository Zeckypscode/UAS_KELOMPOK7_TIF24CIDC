# SiapSen — Aplikasi Absensi Karyawan

## Deskripsi Project
SiapSen adalah aplikasi absensi karyawan yang dibangun secara native menggunakan Kotlin di platform Android. Aplikasi ini dirancang untuk menghadirkan proses pencatatan kehadiran karyawan yang lebih praktis, cepat, dan dapat diandalkan.
Melalui SiapSen, karyawan dapat melakukan absen masuk dan pulang hanya dengan beberapa sentuhan di layar. Setiap absensi tercatat lengkap dengan bukti foto selfie dan titik lokasi (GPS) secara otomatis, sehingga data kehadiran yang dihasilkan lebih akurat dan mudah dipertanggungjawabkan. Selain fitur absensi harian, aplikasi ini juga menghadirkan fitur pengajuan izin dan cuti lengkap dengan kolom alasan, rentang tanggal, dan lampiran dokumen pendukung, sehingga proses administrasi kepegawaian menjadi lebih rapi dan terdokumentasi dengan baik.
Seluruh riwayat kehadiran dan status pengajuan dapat dipantau kapan saja melalui halaman riwayat, yang menampilkan data secara berurutan dan mudah ditelusuri. Dengan antarmuka yang bersih dan alur penggunaan yang sederhana, SiapSen hadir untuk memberikan pengalaman absensi yang efisien dan transparan, baik bagi karyawan maupun pihak manajemen.

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

