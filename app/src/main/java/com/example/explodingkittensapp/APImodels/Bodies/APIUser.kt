package com.example.explodingkittensapp.APImodels.Bodies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class APIUser(
    var email: String,
    var username: String,
    var password: String,
    var total_matches: Int,
    var winrate: Double,
    var friends: MutableList<String>,
    var cards: MutableList<String>,
    var wins: Int,
    var loses: Int,
    var defuses: Int,
    var shuffles: Int,
    var attacks: Int,
    var skips: Int
): Parcelable