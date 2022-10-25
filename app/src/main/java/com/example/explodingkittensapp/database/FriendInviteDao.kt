package com.example.explodingkittensapp.database

import androidx.room.*

@Dao
interface FriendInviteDao {
    @Query("SELECT * FROM friendinviteTable")
    fun getAllFriendInvites(): List<FriendInviteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriendInvite(invite: FriendInviteEntity)

    @Query("SELECT * FROM friendinviteTable WHERE id = :id")
    fun getFriendInvite(id: String): FriendInviteEntity

    @Query("DELETE FROM friendinviteTable WHERE id = :id")
    fun deleteFriendInvite(id: String)
}