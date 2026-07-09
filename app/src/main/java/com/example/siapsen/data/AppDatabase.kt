package com.example.siapsen.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class Converters {
    @TypeConverter
    fun fromAttendanceType(value: AttendanceType): String = value.name

    @TypeConverter
    fun toAttendanceType(value: String): AttendanceType = AttendanceType.valueOf(value)

    @TypeConverter
    fun fromLeaveType(value: LeaveType): String = value.name

    @TypeConverter
    fun toLeaveType(value: String): LeaveType = LeaveType.valueOf(value)

    @TypeConverter
    fun fromLeaveStatus(value: LeaveStatus): String = value.name

    @TypeConverter
    fun toLeaveStatus(value: String): LeaveStatus = LeaveStatus.valueOf(value)
}

@Database(
    entities = [
        AttendanceEntity::class,
        LeaveEntity::class,
        UserEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun attendanceDao(): AttendanceDao
    abstract fun leaveDao(): LeaveDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "siapsen.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
