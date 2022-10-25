package com.example.explodingkittensapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchInviteModel(
    var id: String,
    var matchid: String,
    var invited: String,
    var invitor: String
): Parcelable