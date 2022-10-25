package com.example.explodingkittensapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendInviteModel(
    var id: String,
    var invited: String,
    var invitor: String,
): Parcelable