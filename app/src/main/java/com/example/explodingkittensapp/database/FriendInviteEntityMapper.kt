package com.example.explodingkittensapp.database

import com.example.explodingkittensapp.model.FriendInviteModel

class FriendInviteEntityMapper : EntityMapper<FriendInviteEntity, FriendInviteModel> {
    override fun mapFromCached(type: FriendInviteEntity): FriendInviteModel {
        return FriendInviteModel(
            type.id,
            type.invited,
            type.invitor,
        )
    }

    override fun mapToCached(type: FriendInviteModel): FriendInviteEntity {
        return FriendInviteEntity(
            type.id,
            type.invited,
            type.invitor
        )
    }
}