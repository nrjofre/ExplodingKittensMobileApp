package com.example.explodingkittensapp.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM userTable")
    fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userTable WHERE id = :id")
    fun getUser(id: Long): UserEntity

    @Query("DELETE FROM userTable WHERE id = :id")
    fun deleteUser(id: String)
}