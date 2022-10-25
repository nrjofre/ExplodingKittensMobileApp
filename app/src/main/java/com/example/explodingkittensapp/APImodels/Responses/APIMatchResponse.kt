package com.example.explodingkittensapp.APImodels.Responses

import android.os.Parcelable
import com.example.explodingkittensapp.model.MatchInviteModel
import com.example.explodingkittensapp.model.UserModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class APIMatchResponse(
    val matches: MutableList<MatchInviteModel>
): Parcelable