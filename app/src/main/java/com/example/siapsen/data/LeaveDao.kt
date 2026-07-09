package com.example.siapsen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveDao {

    @Insert
    suspend fun insert(leave: LeaveEntity): Long

    @Query("SELECT * FROM leave_request ORDER BY submittedAt DESC")
    fun getAll(): Flow<List<LeaveEntity>>

    @Query("SELECT * FROM leave_request WHERE status='PENDING'")
    fun getPending(): Flow<List<LeaveEntity>>
    @Query("""UPDATE leave_request SET status=:status WHERE id=:id""")
    suspend fun updateStatus(
        id:Long,
        status:LeaveStatus
    )
}
