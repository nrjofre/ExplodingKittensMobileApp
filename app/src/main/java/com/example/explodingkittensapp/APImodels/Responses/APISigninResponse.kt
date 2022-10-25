package com.example.explodingkittensapp.APImodels.Responses

data class APISigninResponse(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val total_matches: Int,
    val winrate: Double,
    val friends: MutableList<String>,
    val cards: MutableList<String>,
    var lastgame: String,
    val wins: Int,
    val loses: Int,
    val defuses: Int,
    var shuffles: Int,
    var attacks: Int,
    var skips: Int
)
