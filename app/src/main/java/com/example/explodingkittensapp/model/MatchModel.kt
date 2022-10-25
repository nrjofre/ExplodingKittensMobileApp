package com.example.explodingkittensapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchModel(
    var id: String,
    var gamename: String,
    var creator: String,
    var settings: List<String>,
    var participants: List<String>,
    var lastcard: String,
    var turn: Int
): Parcelable