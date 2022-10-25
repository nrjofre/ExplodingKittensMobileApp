package com.example.explodingkittensapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friendInviteTable")
data class FriendInviteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "invited") var invited: String,
    @ColumnInfo(name = "invitor") var invitor: String,

)

