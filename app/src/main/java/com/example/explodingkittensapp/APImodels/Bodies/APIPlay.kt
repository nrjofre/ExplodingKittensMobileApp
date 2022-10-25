package com.example.explodingkittensapp.APImodels.Bodies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class APIPlay(
    var played_card: String,
    var username: String,
    var gamename: String
): Parcelable