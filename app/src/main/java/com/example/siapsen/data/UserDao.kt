package com.example.siapsen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE username=:username AND password=:password LIMIT 1")
    suspend fun login(
        username: String,
        password: String
    ): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getByUsername(username: String): UserEntity?

    @Query("""UPDATE users SET nama = :nama WHERE id = :id""")

    suspend fun updateNama(
        id: Long,
        nama: String
    )
    @Query("""UPDATE users SET password = :password WHERE id = :id """)
    suspend fun updatePassword(
        id: Long,
        password: String
    )
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(
        id: Long
    ): UserEntity?
}