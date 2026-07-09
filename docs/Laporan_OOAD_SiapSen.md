# Laporan OOAD — Aplikasi SiapSen (Absensi Karyawan)

## 1. Deskripsi Aplikasi
SiapSen adalah aplikasi absensi karyawan berbasis Android Native (Kotlin) yang
memungkinkan pengguna melakukan absen masuk/keluar dengan bukti foto dan lokasi
otomatis, mengajukan izin/cuti, serta melihat riwayat absensi dan pengajuan.

## 2. Identifikasi Aktor
- **Karyawan**: pengguna utama aplikasi yang melakukan absensi dan pengajuan izin/cuti.

## 3. Use Case Diagram (deskripsi tekstual)
- Absen Masuk
- Absen Keluar
- Ajukan Izin
- Ajukan Cuti
- Lihat Riwayat Absensi & Izin/Cuti
- Ambil Foto (extend dari Absen Masuk/Keluar dan Ajukan Izin/Cuti)
- Ambil Lokasi Otomatis (extend dari Absen Masuk/Keluar)

## 4. Class Diagram (ringkasan)

### Entity (Model)
- `AttendanceEntity` (id, type, timestamp, photoPath, latitude, longitude, address)
- `LeaveEntity` (id, type, reason, startDate, endDate, attachmentPath, status, submittedAt)
- Enum: `AttendanceType {MASUK, KELUAR}`, `LeaveType {IZIN, CUTI}`, `LeaveStatus {PENDING, DISETUJUI, DITOLAK}`

### Data Access
- `AttendanceDao`, `LeaveDao` (Room DAO, akses ke `AppDatabase`)

### Presentation (Fragment / Activity)
- `MainActivity` — host Activity dengan Navigation Component + BottomNavigationView
- `HomeFragment` — dashboard menu utama
- `AbsenFragment` — proses absen masuk/keluar (kamera + lokasi)
- `IzinCutiFragment` — form pengajuan izin/cuti
- `RiwayatFragment` + `RiwayatAdapter` (RecyclerView) — riwayat gabungan

### Utility
- `LocationHelper` — pembungkus FusedLocationProviderClient + Geocoder
- `ImageUtils` — pembuatan file foto & FileProvider Uri untuk Intent kamera

## 5. Arsitektur Aplikasi
Aplikasi menggunakan pola **MVVM-ringan / Fragment-based architecture**:

```
UI Layer (Activity/Fragment) 
      │  menggunakan ViewBinding
      ▼
Utils Layer (LocationHelper, ImageUtils)
      │
      ▼
Data Layer (Room: AppDatabase → DAO → Entity)
```

Navigasi antar Fragment ditangani oleh **Navigation Component** (Activity + NavHostFragment),
dengan perpindahan data antar layar memakai **Safe Args**. Pengambilan foto memanfaatkan
**Intent implicit ACTION_IMAGE_CAPTURE** yang dibungkus `ActivityResultContracts.TakePicture()`,
sesuai ketentuan komponen native wajib (Activity, Fragment, RecyclerView, Intent).

## 6. Alur Data (Sequence, ringkas)
**Absen Masuk:**
1. User membuka `HomeFragment` → tekan kartu "Absen Masuk".
2. `HomeFragment` mengirim `Intent`/Navigation Action ke `AbsenFragment` (via Safe Args).
3. `AbsenFragment` meminta lokasi via `LocationHelper` (FusedLocationProviderClient).
4. User mengambil foto via `Intent ACTION_IMAGE_CAPTURE`.
5. Data (foto, lokasi, waktu) disimpan ke `AppDatabase` melalui `AttendanceDao`.
6. `RiwayatFragment` menampilkan data baru secara real-time (Room `Flow`).

## 7. Database (Room Schema)

**Tabel `attendance`**
| Kolom | Tipe |
|---|---|
| id | Long (PK, autoGenerate) |
| type | TEXT (MASUK/KELUAR) |
| timestamp | Long |
| photoPath | TEXT |
| latitude | REAL |
| longitude | REAL |
| address | TEXT |

**Tabel `leave_request`**
| Kolom | Tipe |
|---|---|
| id | Long (PK, autoGenerate) |
| type | TEXT (IZIN/CUTI) |
| reason | TEXT |
| startDate | Long |
| endDate | Long |
| attachmentPath | TEXT |
| status | TEXT (PENDING/DISETUJUI/DITOLAK) |
| submittedAt | Long |

## 8. Kesimpulan
Aplikasi SiapSen memenuhi seluruh komponen wajib UAS: dibangun 100% Android Native
Kotlin (bukan cross-platform), memanfaatkan `Activity`, `Fragment`, `RecyclerView`,
dan `Intent`, serta mengimplementasikan lima fitur inti sesuai ketentuan: Absen
Masuk/Keluar, Perizinan & Cuti, Riwayat Absen, Lokasi Otomatis, dan Kamera.
