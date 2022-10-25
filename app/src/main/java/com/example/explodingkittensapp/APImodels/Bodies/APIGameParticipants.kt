package com.example.explodingkittensapp.APImodels.Bodies

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class APIGameParticipants(
    var username: String,
    var gamename: String
): Parcelable
