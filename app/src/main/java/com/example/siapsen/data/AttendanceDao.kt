package com.example.siapsen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Insert
    suspend fun insert(attendance: AttendanceEntity): Long

    @Query("SELECT * FROM attendance ORDER BY timestamp DESC")
    fun getAll(): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE type=:type ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLast(type: AttendanceType): AttendanceEntity?

    @Query("SELECT * FROM attendance ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastAttendance(): AttendanceEntity?

    @Query("SELECT * FROM attendance ORDER BY timestamp DESC")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

}