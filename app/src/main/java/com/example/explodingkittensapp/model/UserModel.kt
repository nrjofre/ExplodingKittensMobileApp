package com.example.explodingkittensapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: String,
    var email: String,
    var username: String,
    var password: String,
    var total_matches: Int,
    var winrate: Double,
    var friends: List<String>,
    var cards: List<String>,
    var lastgame: String,
    var wins: Int,
    var loses: Int,
    var defuses: Int,
    var shuffles: Int,
    var attacks: Int,
    var skips: Int
): Parcelable