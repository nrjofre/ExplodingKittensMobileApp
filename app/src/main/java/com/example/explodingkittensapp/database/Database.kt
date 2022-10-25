package com.example.explodingkittensapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.explodingkittensapp.database.*

@Database(entities = [UserEntity::class, FriendInviteEntity::class], version= 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun friendInviteDao(): FriendInviteDao
}