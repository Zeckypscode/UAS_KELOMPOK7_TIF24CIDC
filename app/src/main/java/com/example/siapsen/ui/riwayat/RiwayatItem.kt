package com.example.siapsen.ui.riwayat

import com.example.siapsen.data.AttendanceEntity
import com.example.siapsen.data.LeaveEntity

/**
 * Sealed class pembungkus dua jenis data riwayat (Absensi & Izin/Cuti)
 * agar dapat ditampilkan bersama dalam satu RecyclerView terurut waktu,
 * masing-masing dengan tampilan (view type) yang berbeda.
 */
sealed class RiwayatItem {
    abstract val timestamp: Long

    data class AbsenItem(val data: AttendanceEntity) : RiwayatItem() {
        override val timestamp: Long get() = data.timestamp
    }

    data class IzinItem(val data: LeaveEntity) : RiwayatItem() {
        override val timestamp: Long get() = data.submittedAt
    }
}
