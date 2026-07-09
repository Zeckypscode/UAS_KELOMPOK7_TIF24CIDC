package com.example.siapsen.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Jenis pengajuan: IZIN atau CUTI */
enum class LeaveType {
    IZIN, CUTI
}

/** Status pengajuan izin/cuti */
enum class LeaveStatus {
    PENDING, DISETUJUI, DITOLAK
}

/**
 * Entity yang merepresentasikan satu pengajuan izin atau cuti,
 * lengkap dengan alasan, rentang tanggal, dan lampiran foto dokumen.
 */
@Entity(tableName = "leave_request")
data class LeaveEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val username: String,

    val nama: String,

    val type: LeaveType,

    val reason: String,

    val startDate: Long,

    val endDate: Long,

    val attachmentPath: String?,

    val status: LeaveStatus = LeaveStatus.PENDING,

    val submittedAt: Long
)
