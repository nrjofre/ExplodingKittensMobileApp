package com.example.explodingkittensapp.database

import com.example.explodingkittensapp.model.UserModel

class UserEntityMapper : EntityMapper<UserEntity, UserModel> {
    override fun mapFromCached(type: UserEntity): UserModel {
        return UserModel(
            type.id,
            type.email,
            type.username,
            type.password,
            type.total_matches,
            type.winrate,
            type.friends,
            type.cards,
            type.lastgame,
            type.wins,
            type.loses,
            type.defuses,
            type.shuffles,
            type.attacks,
            type.skips
        )
    }

    override fun mapToCached(type: UserModel): UserEntity {
        return UserEntity(
            type.id,
            type.email,
            type.username,
            type.password,
            type.total_matches,
            type.winrate,
            type.friends as MutableList<String>,
            type.cards as MutableList<String>,
            type.lastgame,
            type.wins,
            type.loses,
            type.defuses,
            type.shuffles,
            type.attacks,
            type.skips
        )
    }
}