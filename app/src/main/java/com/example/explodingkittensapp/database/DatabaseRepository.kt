package com.example.explodingkittensapp.database

import android.app.Application
import androidx.room.Room
import com.example.explodingkittensapp.model.AppDatabase

class DatabaseRepository(application: Application) {
    private val database = Room.databaseBuilder(application, AppDatabase::class.java,"user").build()

    fun postUserDao(): UserDao {
        return database.userDao()
    }

    fun postFriendInviteDao(): FriendInviteDao {
        return database.friendInviteDao()
    }
}