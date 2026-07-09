package com.example.siapsen.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tipe absensi: MASUK atau KELUAR
 */
enum class AttendanceType {
    MASUK, KELUAR
}

/**
 * Entity yang merepresentasikan satu catatan absensi (masuk/keluar).
 * Menyimpan foto bukti, lokasi otomatis (lat/lng + alamat), dan waktu absen.
 */
@Entity(tableName = "attendance")
data class AttendanceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val username: String,

    val nama: String,

    val type: AttendanceType,

    val timestamp: Long,

    val photoPath: String?,

    val latitude: Double?,

    val longitude: Double?,

    val address: String?
)